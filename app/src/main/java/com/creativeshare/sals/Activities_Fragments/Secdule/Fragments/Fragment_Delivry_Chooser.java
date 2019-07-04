package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Delivry_Chooser extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;

    private Button next;
    private LinearLayout ll_additional_services;
    private ImageView im_additional_Services,arrow1;

    public static Fragment_Delivry_Chooser newInstance() {
        return new Fragment_Delivry_Chooser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivry_choose, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_additional_services = view.findViewById(R.id.ll_additional_Services);
         arrow1=view.findViewById(R.id.arrow1);
        im_additional_Services = view.findViewById(R.id.im_additional_services);
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
                activity.DisplayFragmentconfirmation();
            }
        });

    }


}
