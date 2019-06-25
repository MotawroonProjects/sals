package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;
import com.hbb20.CountryCodePicker;

public class Fragment_Signup extends Fragment {

    private Register_Activity register_activity;
    private CountryCodePicker ccp_choose_country,ccp_country_code;
    private Button bt_apply;
    private LinearLayout ll_service_centers,ll_track_the_shipment;


    public static Fragment_Signup newInstance() {

        return new Fragment_Signup();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
initview(view);
        return view;
    }

    private void initview(View view) {
        register_activity=(Register_Activity)getActivity();
        ccp_choose_country=view.findViewById(R.id.ccp_choose_Country);
        ccp_country_code=view.findViewById(R.id.ccp_country_code);
        bt_apply=view.findViewById(R.id.bt_apply);
        ll_service_centers=view.findViewById(R.id.lll);
        ll_track_the_shipment=view.findViewById(R.id.llll);

        ccp_choose_country.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                try {
                    ccp_country_code.setCountryForNameCode(ccp_choose_country.getSelectedCountryNameCode());

                }catch (StackOverflowError stackOverflowError){

                }
            }
        });
        ccp_country_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                try {
                    ccp_choose_country.setCountryForNameCode(ccp_country_code.getSelectedCountryNameCode());


                }catch (StackOverflowError stackOverflowError){

                }
            }
        });
        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.DisplayFragmentconfirmcode();
            }
        });
        ll_service_centers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.DisplayFragmentServiceCenters();
            }
        });
        ll_track_the_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.DisplayFragmentTrackTheShipment();
            }
        });
    }
}