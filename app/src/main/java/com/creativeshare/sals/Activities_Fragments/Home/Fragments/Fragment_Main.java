package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private Home_Activity activity;
    private ImageView arrow1,arrow2;
    private String current_lang;

    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2=view.findViewById(R.id.arrow2);

        if (current_lang.equals("en")) {
           arrow1.setRotation(180.0f);
           arrow2.setRotation(180.0f);
        }
    arrow2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.DisplayFragmentProfile();
        }
    });

    }

}
