package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Adapter.Spinner_City_Adapter;
import com.creativeshare.sals.Adapter.Spinner_Country_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.CityModel;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.creativeshare.sals.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Shipping_Detials extends Fragment {
    private Scedule_Activity activity;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private Button next, bt_Shipping_dimensions;
    private FrameLayout fr_document, fr_parcel;
    private ImageView im_document, im_parcel;
    private TextView tv_document, tv_parcel;
    private EditText edt_desc;
    private List<CityModel.Cities> cityModelList;
    private Spinner spinner_city_to;
    private Spinner_City_Adapter city_adapter;
    public static Fragment_Shipping_Detials newInstance() {
        return new Fragment_Shipping_Detials();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cityModelList=new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fr_document = view.findViewById(R.id.fr_document);
        fr_parcel = view.findViewById(R.id.fr_parcel);
        im_document = view.findViewById(R.id.im_document);
        im_parcel = view.findViewById(R.id.im_parcel);
        tv_document = view.findViewById(R.id.tv_document);
        tv_parcel = view.findViewById(R.id.tv_parcel);
        edt_desc = view.findViewById(R.id.edt_desc);
        bt_Shipping_dimensions = view.findViewById(R.id.bt_shipping_dimensions);
        next = view.findViewById(R.id.bt_next);
        spinner_city_to = view.findViewById(R.id.sp_cityto);
        city_adapter = new Spinner_City_Adapter(activity, cityModelList);
        spinner_city_to.setAdapter(city_adapter);
        fr_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr_document.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.white));
                im_document.setColorFilter(getResources().getColor(R.color.white));
                im_parcel.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tv_document.setTextColor(getResources().getColor(R.color.white));
                tv_parcel.setTextColor(getResources().getColor(R.color.colorPrimary));
                bt_Shipping_dimensions.setVisibility(View.GONE);
                edt_desc.setVisibility(View.GONE);
            }
        });
        fr_parcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr_document.setBackgroundColor(getResources().getColor(R.color.white));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                im_document.setColorFilter(getResources().getColor(R.color.colorPrimary));
                im_parcel.setColorFilter(getResources().getColor(R.color.white));
                tv_document.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_parcel.setTextColor(getResources().getColor(R.color.white));
                bt_Shipping_dimensions.setVisibility(View.VISIBLE);
                edt_desc.setVisibility(View.GONE);
            }
        });
        bt_Shipping_dimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentShippingDimentions();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentdelivrychooser();
            }
        });

    }
    private void getCities() {

        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .getCity("Bearer"+" "+ userModel.getToken(), current_lang)
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                cityModelList.clear();
                                cityModelList.add(new CityModel.Cities("إختر", "Choose"));
                                cityModelList.addAll(response.body().getCities());
                                city_adapter.notifyDataSetChanged();

                            }
                        } else {
                            try {
                                Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                                Log.e("Error_code", response.code() + "" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });

    }

}
