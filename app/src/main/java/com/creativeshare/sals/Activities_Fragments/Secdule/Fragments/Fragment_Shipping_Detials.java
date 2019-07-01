package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Shipping_Detials extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private Button next;
    private FrameLayout fr_document,fr_parcel;
    private ImageView im_document,im_parcel;
    private TextView tv_document,tv_parcel;

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
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fr_document=view.findViewById(R.id.fr_document);
        fr_parcel=view.findViewById(R.id.fr_parcel);
        im_document=view.findViewById(R.id.im_document);
        im_parcel=view.findViewById(R.id.im_parcel);
        tv_document=view.findViewById(R.id.tv_document);
        tv_parcel=view.findViewById(R.id.tv_parcel);


        fr_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr_document.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.white));
                im_document.setColorFilter(getResources().getColor(R.color.white));
                im_parcel.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tv_document.setTextColor(getResources().getColor(R.color.white));
                tv_parcel.setTextColor(getResources().getColor(R.color.colorPrimary));
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
            }
        });
        next=view.findViewById(R.id.bt_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentdelivrychooser();
            }
        });

    }

}
