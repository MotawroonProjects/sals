package com.creativeshare.sals.activities_fragments.home.fragments.fragment_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Add_Credit_Cart extends Fragment {
    private Home_Activity activity;
    private String current_language;
    private ImageView back_arrow;
    public static Fragment_Add_Credit_Cart newInstance(){
        return new Fragment_Add_Credit_Cart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_credit_card, container, false);

        initview(view);
        return view;
    }

    private void initview(View view) {
        activity=(Home_Activity)getActivity();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow=view.findViewById(R.id.arrow);
        if(current_language.equals("ar")){
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
    }
}
