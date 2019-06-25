package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Language.Language;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Language extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow, im_ar, im_en;
    private LinearLayout ll_arabic, ll_english;
    private String current_lang;

    public static Fragment_Language newInstance() {
        return new Fragment_Language();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        im_ar = view.findViewById(R.id.im_ar);
        im_en= view.findViewById(R.id.im_en);
        ll_arabic=view.findViewById(R.id.ll_arabic);
        ll_english=view.findViewById(R.id.ll_english);


        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
            ll_arabic.setBackgroundColor(getResources().getColor(R.color.white));
            im_ar.setVisibility(View.VISIBLE);
            im_en.setVisibility(View.GONE);
            ll_english.setBackgroundColor(getResources().getColor(R.color.gray4));
        }
        else{
            ll_arabic.setBackgroundColor(getResources().getColor(R.color.gray4));
            im_ar.setVisibility(View.GONE);
            im_en.setVisibility(View.VISIBLE);
            ll_english.setBackgroundColor(getResources().getColor(R.color.white));
        }
        ll_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_arabic.setBackgroundColor(getResources().getColor(R.color.white));
                im_ar.setVisibility(View.VISIBLE);
                im_en.setVisibility(View.GONE);
                ll_english.setBackgroundColor(getResources().getColor(R.color.gray4));
                activity.RefreshActivity("ar");
            }
        });
        ll_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_arabic.setBackgroundColor(getResources().getColor(R.color.gray4));
                im_ar.setVisibility(View.GONE);
                im_en.setVisibility(View.VISIBLE);
                ll_english.setBackgroundColor(getResources().getColor(R.color.white));
                activity.RefreshActivity("en");
            }
        });
back_arrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        activity.Back();
    }
});
    }


}
