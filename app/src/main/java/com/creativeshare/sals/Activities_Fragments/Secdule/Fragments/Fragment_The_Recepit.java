package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Adapter.Bike_Adapter;
import com.creativeshare.sals.Adapter.Help_Cat_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.models.Help_Cat_Model;
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

public class Fragment_The_Recepit extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private LinearLayout ll_search_for_address;
   // private FrameLayout fr_shape1,fr_shape2,fr_shape3;
    private ImageView arrow_search_for_address,arrow1,arrow2;
   // im_shape1,im_shape2,im_shape3,
   // private TextView tv_shape1,tv_shape2,tv_shape3;
    private Button next;
    private RecyclerView rec_bike;
    private List<Bike_Model.Sizes> sizes;
    private Bike_Adapter bike_adapter;
    private Preferences preferences;
    private UserModel userModel;
    public static Fragment_The_Recepit newInstance() {
        return new Fragment_The_Recepit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_receipt, container, false);
        initView(view);
        getBike();
        return view;
    }
    private void initView(View view) {
        sizes=new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
      //  fr_shape1=view.findViewById(R.id.fr_shape1);
        //fr_shape2=view.findViewById(R.id.fr_shape2);
        //fr_shape3=view.findViewById(R.id.fr_shape3);
        //im_shape1=view.findViewById(R.id.im_shape1);
        //im_shape2=view.findViewById(R.id.im_shape2);
        //im_shape3=view.findViewById(R.id.im_shape3);
        //tv_shape1=view.findViewById(R.id.tv_shape1);
        //tv_shape2=view.findViewById(R.id.tv_shape2);
        //tv_shape3=view.findViewById(R.id.tv_shape3);
        arrow_search_for_address=view.findViewById(R.id.arrow3);
        arrow1=view.findViewById(R.id.arrow1);
        arrow2=view.findViewById(R.id.arrow2);
        rec_bike=view.findViewById(R.id.rec_bike);
        bike_adapter=new Bike_Adapter(sizes,activity);
        rec_bike.setLayoutManager(new LinearLayoutManager(activity,RecyclerView.HORIZONTAL,true));
        rec_bike.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rec_bike.setDrawingCacheEnabled(true);
        rec_bike.setItemViewCacheSize(25);
        rec_bike.setAdapter(bike_adapter);
        ll_search_for_address=view.findViewById(R.id.ll_search_for_address);
        if(current_lang.equals("en")){
            arrow_search_for_address.setRotation(180.0f);
            arrow1.setRotation(180.0f);
            arrow2.setRotation(180.0f);
        }
        ll_search_for_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentSearchforaddress();
            }
        });
        /*
fr_shape1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape1.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape1.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));



    }
});
fr_shape2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape2.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape2.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));
    }
});
fr_shape3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape3.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape3.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
    }
});*/
        next=view.findViewById(R.id.bt_next);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


            activity.DisplayFragmentshippingdetilas();

    }
});

    }
    private void getBike() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getBike("Bearer" + " " + userModel.getToken(),current_lang).enqueue(new Callback<Bike_Model>() {
            @Override
            public void onResponse(Call<Bike_Model> call, Response<Bike_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getSizes().size()>0) {
                    sizes.clear();
                    sizes.addAll(response.body().getSizes());
                    bike_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Bike_Model> call, Throwable t) {
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

}
