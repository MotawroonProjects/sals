package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Secdule extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private ImageView im1, im2, im3,back_arrow;

    public static Fragment_Secdule newInstance() {
        return new Fragment_Secdule();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scdule, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        im1 = view.findViewById(R.id.im1);
        im2 = view.findViewById(R.id.im2);
        im3 = view.findViewById(R.id.im3);
        back_arrow = view.findViewById(R.id.arrow);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });

    }
    public void update(int pos){
        if(pos==0){
            im1.setColorFilter(getResources().getColor(R.color.colorPrimary));
            im2.setColorFilter(getResources().getColor(R.color.gray4));
            im3.setColorFilter(getResources().getColor(R.color.gray4));
        }
        else if(pos==1){

            im2.setColorFilter(getResources().getColor(R.color.colorPrimary));
            im3.setColorFilter(getResources().getColor(R.color.gray4));
        }

    }
    public void update2(int pos){
        if(pos==0){
            im1.setColorFilter(getResources().getColor(R.color.colorPrimary));

        }
        else if(pos==1){

            im2.setColorFilter(getResources().getColor(R.color.colorPrimary));

        }
        else if(pos==2){
            im3.setColorFilter(getResources().getColor(R.color.colorPrimary));

        }
    }

}
