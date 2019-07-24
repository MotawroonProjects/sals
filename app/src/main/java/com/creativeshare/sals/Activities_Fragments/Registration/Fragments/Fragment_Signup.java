package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.remote.Api;
import com.hbb20.CountryCodePicker;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Signup extends Fragment {

    private Register_Activity register_activity;
    private CountryCodePicker ccp_choose_country,ccp_country_code;
    private EditText edt_phone;
    private Button bt_apply;
    private LinearLayout ll_service_centers,ll_track_the_shipment;


    public static Fragment_Signup newInstance() {

        return new Fragment_Signup();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
initview(view);
        return view;
    }

    private void initview(View view) {
        register_activity=(Register_Activity)getActivity();
        edt_phone=view.findViewById(R.id.edt_phone);
        ccp_choose_country=view.findViewById(R.id.ccp_choose_Country);
        ccp_country_code=view.findViewById(R.id.ccp_country_code);
        bt_apply=view.findViewById(R.id.bt_apply);
        ll_service_centers=view.findViewById(R.id.lll);
        ll_track_the_shipment=view.findViewById(R.id.llll);
ccp_country_code.registerCarrierNumberEditText(edt_phone);
        ccp_choose_country.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                try {
                    ccp_country_code.setCountryForNameCode(ccp_choose_country.getSelectedCountryNameCode());

                }catch (StackOverflowError stackOverflowError){

                }
            }
        });
        ccp_country_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                try {
                    ccp_choose_country.setCountryForNameCode(ccp_country_code.getSelectedCountryNameCode());


                }catch (StackOverflowError stackOverflowError){

                }
            }
        });
        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
                //register_activity.DisplayFragmentconfirmcode();
            }
        });
        ll_service_centers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.DisplayFragmentServiceCenters();
            }
        });
        ll_track_the_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.DisplayFragmentTrackTheShipment();
            }
        });
    }

    private void checkdata() {

String phone=edt_phone.getText().toString();
String phone_code=ccp_country_code.getSelectedCountryCode().replace("+","00");
if(TextUtils.isEmpty(phone)||!ccp_country_code.isValidFullNumber()){
    edt_phone.setError(getResources().getString(R.string.field_req));
}
else {
if(phone.startsWith("0")){
    phone=phone.replaceFirst("0","");
    edt_phone.setError(null);
Login(phone,phone_code);
}
}
    }

    private void Login(String phone, String phone_code) {
        final ProgressDialog progressDialog= Common.createProgressDialog(register_activity,getResources().getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Api.getService().SignIn(phone_code,phone,"1").enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
progressDialog.dismiss();
if(response.isSuccessful()){
    register_activity.DisplayFragmentconfirmcode(phone,phone_code);
}
else {
    try {
        Log.e("error_code",response.code()+"_"+response.errorBody().string());
    } catch (IOException e) {
        e.printStackTrace();
    }
    if(response.code()==422){
        Common.CreateSignAlertDialog(register_activity,getResources().getString(R.string.user_is_logedin));
    }
    else {
    Toast.makeText(register_activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();}

}
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(register_activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                Log.e("Error",t.getMessage()+t.getLocalizedMessage());
            }
        });
    }
}