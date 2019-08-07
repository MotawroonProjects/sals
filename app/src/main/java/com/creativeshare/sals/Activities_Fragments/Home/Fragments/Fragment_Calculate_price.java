package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Calculate_price extends Fragment {
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
    private FrameLayout fr_document,fr_parcel;
    private ImageView im_document,im_parcel;
    private TextView tv_document,tv_parcel;
private Button bt_claculate,bt_Shipping_dimensions;
    private EditText edt_desc;

    public static Fragment_Calculate_price newInstance() {
        return new Fragment_Calculate_price();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_price, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow=view.findViewById(R.id.arrow);
        fr_document=view.findViewById(R.id.fr_document);
        fr_parcel=view.findViewById(R.id.fr_parcel);
        im_document=view.findViewById(R.id.im_document);
        im_parcel=view.findViewById(R.id.im_parcel);
        tv_document=view.findViewById(R.id.tv_document);
        tv_parcel=view.findViewById(R.id.tv_parcel);
        bt_claculate=view.findViewById(R.id.bt_claculate);
        edt_desc=view.findViewById(R.id.edt_desc);
        bt_Shipping_dimensions=view.findViewById(R.id.bt_shipping_dimensions);
        if(current_lang.equals("ar")){
            back_arrow.setRotation(180.0f);
        }



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
                edt_desc.setVisibility(View.VISIBLE);
            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        bt_claculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentComputrizedprice();
            }
        });
        bt_Shipping_dimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentShippingDimentions();
            }
        });
    }

}
