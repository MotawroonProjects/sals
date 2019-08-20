package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

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

import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Computrized_Model;
import com.creativeshare.sals.models.Shipment_Send_Model;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Delivry_Chooser extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;

    private Button next;
    private LinearLayout ll_additional_services;
    private ImageView im_additional_Services,arrow1;
    private TextView tv_document,  tv_cityt, tv_day, tv_num, tv_total_pricedhl,tv_address;

    public static Fragment_Delivry_Chooser newInstance() {
        return new Fragment_Delivry_Chooser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivry_choose, container, false);
        initView(view);
        applydata();
        return view;
    }

    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_additional_services = view.findViewById(R.id.ll_additional_Services);
         arrow1=view.findViewById(R.id.arrow1);
        im_additional_Services = view.findViewById(R.id.im_additional_services);
        tv_address=view.findViewById(R.id.tv_address);
        tv_document = view.findViewById(R.id.tv_document);
     //   tv_cityf = view.findViewById(R.id.tv_cityf);
        tv_cityt = view.findViewById(R.id.tv_cityt);
        tv_num = view.findViewById(R.id.tv_num);
        tv_day = view.findViewById(R.id.tv_day);
        tv_total_pricedhl = view.findViewById(R.id.tv_total_pricedhl);
     //   tv_time = view.findViewById(R.id.tv_times);
        next = view.findViewById(R.id.bt_next);

        if (current_lang.equals("en")) {
            im_additional_Services.setRotation(180.0f);
            arrow1.setRotation(180.0f);
        }
        ll_additional_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentAdditionalservices();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //activity.DisplayFragmentconfirmation();
            }
        });

    }
    private void applydata() {
        if(Shipment_Send_Model.getParcel().equals("0")){
            tv_document.setText(getResources().getString(R.string.documents));

        }
        else {
            tv_document.setText(getResources().getString(R.string.parcels));

        }
       // tv_cityf.setText(Computrized_Model.getCity_From());
        tv_address.setText(Shipment_Send_Model.getAdddresst());
        tv_cityt.setText(Shipment_Send_Model.getCityt());
        tv_num.setText(Shipment_Send_Model.getWegights().size() + getResources().getString(R.string.pieces) +Shipment_Send_Model.getWegights().get(0) + getResources().getString(R.string.kg));
        tv_day.setText(getResources().getString(R.string.Delivery)+Shipment_Send_Model.getDay_number()+getResources().getString(R.string.days));
        tv_total_pricedhl.setText(getResources().getString(R.string.from_dhl)+Shipment_Send_Model.getPrice() + getResources().getString(R.string.ryal));
       // tv_day.setText(Computrized_Model.getTime());
    }

}
