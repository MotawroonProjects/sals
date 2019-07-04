package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

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

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Computrized_Price extends Fragment {
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow,im_additionalservices;
    private LinearLayout ll_next;
    public static Fragment_Computrized_Price newInstance() {
        return new Fragment_Computrized_Price();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computerized_price, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow=view.findViewById(R.id.arrow);
        im_additionalservices=view.findViewById(R.id.im_additional_services);
        ll_next=view.findViewById(R.id.ll_next);
        if(current_lang.equals("ar")){
            back_arrow.setRotation(180.0f);

        }
        else {
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
