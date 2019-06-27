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
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Track_The_Shipment;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Search_For_Address extends Fragment {
    private Scedule_Activity activity;
   private Home_Activity home_activity;
    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";
    public static Fragment_Search_For_Address newInstance(int param) {
        Fragment_Search_For_Address fragment_search_for_address=new Fragment_Search_For_Address();
        Bundle bundle=new Bundle();
        bundle.putInt(Tag,param);
        fragment_search_for_address.setArguments(bundle);
        return fragment_search_for_address;    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_for_address, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        param=getArguments().getInt(Tag);
        if(param==1){
            home_activity = (Home_Activity) getActivity();
            Paper.init(home_activity);}
        else{
            activity = (Scedule_Activity) getActivity();
            Paper.init(activity);
        }
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        back_arrow = view.findViewById(R.id.arrow);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(param==1){
                home_activity.Back();}
                else {
                    activity.Back();
                }
            }
        });

    }


}
