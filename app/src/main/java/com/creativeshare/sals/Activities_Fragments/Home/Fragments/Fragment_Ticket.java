package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Adapter.Help_Cat_Adapter;
import com.creativeshare.sals.Adapter.Question_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.models.Questions_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Ticket extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow,arrow5,arrow6;
    private EditText edt_desc,edt_email;
    private TextView tv_shipment;
    private LinearLayout ll_shipment,ll_cat;
    private Button bt_yes,bt_no;
    private SwitchCompat sw_call;
    private String current_lang;
private int Shipmentid,cat_id;
private Preferences preferences;
private UserModel userModel;
    public static Fragment_Ticket newInstance() {
        return new Fragment_Ticket();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
initView(view);
        return view;
    }



    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        arrow5=view.findViewById(R.id.arrow5);
        arrow6=view.findViewById(R.id.arrow6);
bt_no=view.findViewById(R.id.bt_no);
bt_yes=view.findViewById(R.id.bt_yes);
ll_shipment=view.findViewById(R.id.ll_shipment);
ll_cat=view.findViewById(R.id.ll_cat);
tv_shipment=view.findViewById(R.id.tv_ship);
edt_desc=view.findViewById(R.id.edt_desc);
edt_email=view.findViewById(R.id.edt_email);
bt_no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bt_no.setBackgroundColor(getResources().getColor(R.color.blue));
        bt_no.setTextColor(getResources().getColor(R.color.white));
        bt_yes.setBackgroundColor(getResources().getColor(R.color.white));
        bt_yes.setTextColor(getResources().getColor(R.color.black));
        ll_shipment.setVisibility(View.INVISIBLE);
        tv_shipment.setVisibility(View.INVISIBLE);
    }
});
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_yes.setBackgroundColor(getResources().getColor(R.color.blue));
                bt_yes.setTextColor(getResources().getColor(R.color.white));
                bt_no.setBackgroundColor(getResources().getColor(R.color.white));
                bt_no.setTextColor(getResources().getColor(R.color.black));
                ll_shipment.setVisibility(View.VISIBLE);
                tv_shipment.setVisibility(View.VISIBLE);
            }
        });
        ll_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentshipments();
            }
        });
        ll_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentIssue();
            }
        });
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        else {
            arrow5.setRotation(180.0f);
            arrow6.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
    }

    public void setid(int data) {
        Shipmentid=data;
    }

    public void setcatid(int id) {
        cat_id=id;
    }
}