package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.creativeshare.sals.models.Quote_Model;
import com.creativeshare.sals.models.Ticket_Model;
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
    private ImageView back_arrow, arrow5, arrow6;
    private EditText edt_desc, edt_email;
    private TextView tv_shipment;
    private LinearLayout ll_shipment, ll_cat;
    private Button bt_yes, bt_no, bt_send;
    private SwitchCompat sw_call;
    private String current_lang;
    private int Shipmentid = 0, cat_id, related = 0, calable = 0, order_type;

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
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        arrow5 = view.findViewById(R.id.arrow5);
        arrow6 = view.findViewById(R.id.arrow6);
        bt_no = view.findViewById(R.id.bt_no);
        bt_yes = view.findViewById(R.id.bt_yes);
        bt_send = view.findViewById(R.id.bt_send);
        ll_shipment = view.findViewById(R.id.ll_shipment);
        ll_cat = view.findViewById(R.id.ll_cat);
        tv_shipment = view.findViewById(R.id.tv_ship);
        edt_desc = view.findViewById(R.id.edt_desc);
        edt_email = view.findViewById(R.id.edt_email);
        sw_call = view.findViewById(R.id.switch_call);
        sw_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calable == 0) {
                    calable = 1;
                    sw_call.setChecked(true);
                } else {
                    calable = 0;
                    sw_call.setChecked(false);
                }

            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_no.setBackgroundColor(getResources().getColor(R.color.blue));
                bt_no.setTextColor(getResources().getColor(R.color.white));
                bt_yes.setBackgroundColor(getResources().getColor(R.color.white));
                bt_yes.setTextColor(getResources().getColor(R.color.black));
                ll_shipment.setVisibility(View.GONE);
                tv_shipment.setVisibility(View.GONE);
                related = 0;
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
                related = 1;
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
        } else {
            arrow5.setRotation(180.0f);
            arrow6.setRotation(180.0f);
        }

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
    }

    private void checkdata() {
String desc=edt_desc.getText().toString();
String email=edt_email.getText().toString();

if(!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches()&&((related==1&&Shipmentid!=0)||related==0)&&cat_id!=0){
    sendissue(desc,email);
}
else {
    if(TextUtils.isEmpty(desc)){
        edt_desc.setError(getResources().getString(R.string.field_req));
    }
    if(TextUtils.isEmpty(email)){
        edt_email.setError(getResources().getString(R.string.field_req));
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        edt_email.setError(getResources().getString(R.string.error_Email));

    }
    if(cat_id==0){
        Common.CreateSignAlertDialog(activity,getResources().getString(R.string.addissue));
    }
    if(related==1&&Shipmentid==0){
        Common.CreateSignAlertDialog(activity,getResources().getString(R.string.Shipment));

    }
}
    }

    private void sendissue(String desc, String email) {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().createticket("Bearer"+" "+ userModel.getToken(),email,desc,related+"",order_type+"",calable+"",cat_id+"",Shipmentid+"").enqueue(new Callback<Ticket_Model>() {
            @Override
            public void onResponse(Call<Ticket_Model> call, Response<Ticket_Model> response) {

                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(activity,getResources().getString(R.string.success),Toast.LENGTH_LONG).show();
                    activity.Back();
                    //updateappcommission(response.body());
                }
                else {
                    Log.e("ll",response.code()+""+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Ticket_Model> call, Throwable t) {
                try {
                    dialog.dismiss();
                    // smoothprogressbar.setVisibility(View.GONE);
                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage());
                } catch (Exception e) {
                }
            }
        });
    }

    public void setid(int id, int data) {
        Shipmentid = id;
        order_type = data;
    }

    public void setcatid(int id) {
        cat_id = id;
    }
}