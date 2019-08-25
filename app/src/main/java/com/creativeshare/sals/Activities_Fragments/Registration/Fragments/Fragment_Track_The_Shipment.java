package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.Adapter.Track_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Orders_Model;
import com.creativeshare.sals.models.Track_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Track_The_Shipment extends Fragment {
    private Register_Activity register_activity;
    private Home_Activity activity;

    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";
    private RecyclerView rec_track;
    private List<Track_Model.AWBInfo> awbInfos;
    private Track_Adapter track_adapter;
    private ProgressBar progBar;
    private LinearLayout ll_no_order;
    private EditText edt_awbnum;
    public static Fragment_Track_The_Shipment newInstance(int param) {

      Fragment_Track_The_Shipment fragment_track_the_shipment=new Fragment_Track_The_Shipment();
      Bundle bundle=new Bundle();
      bundle.putInt(Tag,param);
      fragment_track_the_shipment.setArguments(bundle);
      return fragment_track_the_shipment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_the_shipment, container, false);
        initview(view);

        return view;
    }

    private void initview(View view) {

        param=getArguments().getInt(Tag);
        awbInfos =new ArrayList<>();
        if(param==1){
        register_activity = (Register_Activity) getActivity();
        Paper.init(register_activity);}
        else{
            activity = (Home_Activity) getActivity();
            Paper.init(activity);
        }

        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        rec_track=view.findViewById(R.id.rec_track);
        progBar = view.findViewById(R.id.progBar);
        ll_no_order = view.findViewById(R.id.ll_no_order);
        edt_awbnum=view.findViewById(R.id.edt_awbnum);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progBar.setVisibility(View.GONE);
        ll_no_order.setVisibility(View.VISIBLE);
        track_adapter=new Track_Adapter(awbInfos,getActivity());
        rec_track.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rec_track.setAdapter(track_adapter);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(

                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(param==1)
                {register_activity.Back();}
                else{
                    activity.Back();
                }
            }
        });
        edt_awbnum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = edt_awbnum.getText().toString();
                    if (!TextUtils.isEmpty(query)) {
                        getorders(query);
                        return true;
                    }
                }
                return false;
            }
        });    }
    public void getorders(String awbnum) {
        //   Common.CloseKeyBoard(homeActivity, edt_name);
        Common.CloseKeyBoard(getActivity(),edt_awbnum);
        // rec_sent.setVisibility(View.GONE);
        progBar.setVisibility(View.VISIBLE);
        ll_no_order.setVisibility(View.GONE);

        Api.getService()
                .track(awbnum)
                .enqueue(new Callback<Track_Model>() {
                    @Override
                    public void onResponse(Call<Track_Model> call, Response<Track_Model> response) {
                        progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null && response.body().getAWBInfo() != null) {
                            awbInfos.clear();
                            awbInfos.addAll(response.body().getAWBInfo());
                            if (response.body().getAWBInfo().size() > 0) {
                                // rec_sent.setVisibility(View.VISIBLE);

                                ll_no_order.setVisibility(View.GONE);
                                track_adapter.notifyDataSetChanged();
                                //   total_page = response.body().getMeta().getLast_page();

                            } else {
                                ll_no_order.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            try {
                                Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Track_Model> call, Throwable t) {
                        try {

                            progBar.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("error", t.getMessage());
                        } catch (Exception e) {
                        }
                    }
                });

    }


}