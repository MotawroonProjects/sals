package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_The_Recepit extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private LinearLayout ll_search_for_address;
    private FrameLayout fr_shape1,fr_shape2,fr_shape3;
    private ImageView im_shape1,im_shape2,im_shape3,arrow_search_for_address,arrow1,arrow2;
    private TextView tv_shape1,tv_shape2,tv_shape3;
    private Button next;

    public static Fragment_The_Recepit newInstance() {
        return new Fragment_The_Recepit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_receipt, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fr_shape1=view.findViewById(R.id.fr_shape1);
        fr_shape2=view.findViewById(R.id.fr_shape2);
        fr_shape3=view.findViewById(R.id.fr_shape3);
        im_shape1=view.findViewById(R.id.im_shape1);
        im_shape2=view.findViewById(R.id.im_shape2);
        im_shape3=view.findViewById(R.id.im_shape3);
        tv_shape1=view.findViewById(R.id.tv_shape1);
        tv_shape2=view.findViewById(R.id.tv_shape2);
        tv_shape3=view.findViewById(R.id.tv_shape3);
        arrow_search_for_address=view.findViewById(R.id.arrow3);
        arrow1=view.findViewById(R.id.arrow1);
        arrow2=view.findViewById(R.id.arrow2);

        ll_search_for_address=view.findViewById(R.id.ll_search_for_address);
        if(current_lang.equals("en")){
            arrow_search_for_address.setRotation(180.0f);
            arrow1.setRotation(180.0f);
            arrow2.setRotation(180.0f);
        }
        ll_search_for_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentSearchforaddress();
            }
        });
fr_shape1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape1.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape1.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));



    }
});
fr_shape2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape2.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape2.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));
    }
});
fr_shape3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape3.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape3.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
    }
});
        next=view.findViewById(R.id.bt_next);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(activity.param==1){
        activity.DisplayFragmentshippingdetilas();
        activity.DisplayFragmentdelivrychooser();
        activity.DisplayFragmentconfirmation();
        }
        else{
            activity.DisplayFragmentshippingdetilas();
        }
    }
});

    }

}
