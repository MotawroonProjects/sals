package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Computrized_Model;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Computrized_Price extends Fragment {
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow, im_additionalservices;
    private TextView tv_document, tv_cityf, tv_cityt, tv_day, tv_num, tv_price, tv_time;
    private LinearLayout ll_next;

    public static Fragment_Computrized_Price newInstance() {
        return new Fragment_Computrized_Price();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computerized_price, container, false);
        initView(view);
        applydata();
        return view;
    }

    private void applydata() {
        tv_document.setText(Computrized_Model.getType());
        tv_cityf.setText(Computrized_Model.getCity_From());
        tv_cityt.setText(Computrized_Model.getCity_to());
        tv_num.setText(Computrized_Model.getQuantity() + getResources().getString(R.string.pieces) + Computrized_Model.getWeight() + getResources().getString(R.string.kg));
        tv_day.setText(getResources().getString(R.string.Delivery)+Computrized_Model.getDay_number()+getResources().getString(R.string.days));
        tv_price.setText(Computrized_Model.getPrice() + getResources().getString(R.string.ryal));
        tv_time.setText(Computrized_Model.getTime());
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        im_additionalservices = view.findViewById(R.id.im_additional_services);
        ll_next = view.findViewById(R.id.ll_next);
        tv_document = view.findViewById(R.id.tv_document);
        tv_cityf = view.findViewById(R.id.tv_cityf);
        tv_cityt = view.findViewById(R.id.tv_cityt);
        tv_num = view.findViewById(R.id.tv_num);
        tv_day = view.findViewById(R.id.tv_day);
        tv_price = view.findViewById(R.id.tv_price);
        tv_time = view.findViewById(R.id.tv_times);

        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);

        } else {
            im_additionalservices.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startscdeule(1);
            }
        });

    }

}
