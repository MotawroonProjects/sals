package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Track_The_Shipment extends Fragment {
    private Register_Activity register_activity;
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";

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
        if(param==1){
        register_activity = (Register_Activity) getActivity();
        Paper.init(register_activity);}
        else{
            activity = (Home_Activity) getActivity();
            Paper.init(activity);
        }
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
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
    }


}