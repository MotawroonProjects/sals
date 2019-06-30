package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Profile extends Fragment {
    private Home_Activity activity;
    private ImageView arrow1,arrow2,arrow3,arrow4,arrow5,arrow6,back_arrow;
   private ConstraintLayout co_name,co_address,co_phone,co_email,co_language;
   private LinearLayout ll_mange_card;
    private String current_lang;

    public static Fragment_Profile newInstance() {
        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {

        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow=view.findViewById(R.id.arrow);
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2=view.findViewById(R.id.arrow2);
        arrow3 = view.findViewById(R.id.arrow3);
        arrow4=view.findViewById(R.id.arrow4);
        arrow5 = view.findViewById(R.id.arrow5);
        arrow6=view.findViewById(R.id.arrow6);
        co_address=view.findViewById(R.id.co_address);
        co_email=view.findViewById(R.id.co_email);
        co_name=view.findViewById(R.id.co_name);
        co_phone=view.findViewById(R.id.co_phone);
        co_language=view.findViewById(R.id.co_language);
        ll_mange_card=view.findViewById(R.id.ll_mangecard);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);}
        else  if(current_lang.equals("en")) {
           arrow1.setRotation(180.0f);
           arrow2.setRotation(180.0f);
            arrow3.setRotation(180.0f);
            arrow4.setRotation(180.0f);
            arrow5.setRotation(180.0f);
            arrow6.setRotation(180.0f);
        }
        co_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentMyaddress();
            }
        });
       co_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEditphone();
            }
        });
       co_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEmailAddress();
            }
        });
       ll_mange_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentAddcreditCard();
            }
        });
       co_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEditname();

            }
        });
  co_language.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.DisplayFragmentLanguage();
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
