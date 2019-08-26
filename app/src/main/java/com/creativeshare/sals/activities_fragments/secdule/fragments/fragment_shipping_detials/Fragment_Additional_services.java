package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments.fragment_shipping_detials;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Adapter.Other_Services_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Other_Services_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Additional_services extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
    private RecyclerView rec_other_services;
    private List<Other_Services_Model.Services> servicesList;
    private Other_Services_Adapter other_services_adapter;
    private Preferences preferences;
    private UserModel userModel;
    public static Fragment_Additional_services newInstance() {
        return new Fragment_Additional_services();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_services, container, false);
        initView(view);
        getotherservices();
        return view;
    }

    private void getotherservices() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getOtherservices("Bearer" + " " + userModel.getToken(),current_lang).enqueue(new Callback<Other_Services_Model>() {
            @Override
            public void onResponse(Call<Other_Services_Model> call, Response<Other_Services_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getServices().size()>0) {
                  servicesList.clear();
                    servicesList.addAll(response.body().getServices());
                  other_services_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Other_Services_Model> call, Throwable t) {
                //progBar.setVisibility(View.GONE);
                dialog.dismiss();
                try {
                    Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
                    Log.e("Error ", t.getMessage());

                } catch (Exception e) {

                }
            }
        });

    }

    private void initView(View view) {
        servicesList=new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
rec_other_services=view.findViewById(R.id.rec_other);
other_services_adapter=new Other_Services_Adapter(servicesList,activity);
rec_other_services.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
rec_other_services.setItemViewCacheSize(25);
rec_other_services.setDrawingCacheEnabled(true);
rec_other_services.setLayoutManager(new GridLayoutManager(activity,1));
rec_other_services.setAdapter(other_services_adapter);

        back_arrow = view.findViewById(R.id.arrow);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });

    }


}
