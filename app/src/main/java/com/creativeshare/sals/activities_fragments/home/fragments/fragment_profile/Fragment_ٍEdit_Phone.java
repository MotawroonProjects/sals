package com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_ٍEdit_Phone extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
private Preferences preferences;
private UserModel userModel;
private TextView tv_phone,tv_link;
    public static Fragment_ٍEdit_Phone newInstance() {
        return new Fragment_ٍEdit_Phone();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_phone, container, false);
initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        tv_phone=view.findViewById(R.id.tv_phone);
        if(userModel.getUser().getMobile_number()!=null){
            tv_phone.setText(userModel.getUser().getMobile_code()+userModel.getUser().getMobile_number());
        }
        tv_link=view.findViewById(R.id.tv_link);
        tv_link.setPaintFlags(tv_link.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
activity.startcustomactivity();
            }
        });
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
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
}