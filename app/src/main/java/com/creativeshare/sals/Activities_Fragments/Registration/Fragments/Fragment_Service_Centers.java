package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Service_Centers extends Fragment {
    private Register_Activity register_activity;
    private String current_lang;
    private ImageView back_arrow;

    public static Fragment_Service_Centers newInstance() {

        return new Fragment_Service_Centers();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_centers, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        register_activity = (Register_Activity) getActivity();
        Paper.init(register_activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow=view.findViewById(R.id.arrow);
        if(current_lang.equals("ar")){
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.Back();
            }
        });
    }
}
