package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Pay_Model;
import com.creativeshare.sals.models.Payment_Result_Model;
import com.creativeshare.sals.models.Quote_Model;
import com.creativeshare.sals.models.Shipment_Send_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Confirmation extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private Preferences preferences;
    private UserModel userModel;
private EditText edt_name,edt_num,edt_cvc;
private Button bt_confirm;
    public static Fragment_Confirmation newInstance() {
        return new Fragment_Confirmation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
edt_name=view.findViewById(R.id.edt_name);
edt_num=view.findViewById(R.id.edt_num);
edt_cvc=view.findViewById(R.id.edt_cvc);
bt_confirm=view.findViewById(R.id.bt_confirm);
bt_confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        checkdata();
    }
});
    }

    private void checkdata() {
        String name=edt_name.getText().toString();
        String num=edt_num.getText().toString();
        String cvc=edt_cvc.getText().toString();
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(num)&&!TextUtils.isEmpty(cvc)){
            pay(name,num,cvc);
        }
        else {
            if(TextUtils.isEmpty(name)){
                edt_name.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(num)){
                edt_num.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(cvc)){
                edt_cvc.setError(getResources().getString(R.string.field_req));
            }
        }
    }

    private void pay(String name, String num, String cvc) {
        Pay_Model pay_model=new Pay_Model();
        pay_model.getSource().setName(name);
        pay_model.getSource().setNumber(num);
        pay_model.getSource().setCvc(Integer.parseInt(cvc));
        pay_model.setAmount(Double.parseDouble(Shipment_Send_Model.getPrice()));
        pay_model.getSource().setMonth(Calendar.MONTH+1);
        pay_model.getSource().setYear(Calendar.YEAR);
        pay_model.getSource().setType("creditcard");

            //  Log.e("data", Shipment_Send_Model.getDate()+wegights+is_dutiable+Shipment_Send_Model.getTime()+ready_time_gmt_offset+dimension_unit+weight_unit+payment_country_code+Shipment_Send_Model.getFromcountrycode()+Shipment_Send_Model.getCityf()+to_city+to_country_code);
            final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService().Payship("Bearer"+" "+ userModel.getToken(), pay_model).enqueue(new Callback<Payment_Result_Model>() {
                @Override
                public void onResponse(Call<Payment_Result_Model> call, Response<Payment_Result_Model> response) {
                    dialog.dismiss();
                    if(response.isSuccessful()){
                        //     assert response.body() != null;
                        //  Log.e("price",response.body().getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
                        //  activity.DisplayFragmentComputrizedprice();
                  /*  if (response.body() != null) {
                        Log.e("ss",response.body().toString()+response.raw()+response.headers()+response.body().getData().getGetQuoteResponse().getBkgDetails());
                    }*/
                  makeshipment();

                    }
                    else {
                        try {
                            Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                            Log.e("Error_code", response.code() + "" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Payment_Result_Model> call, Throwable t) {
                    try {
                        dialog.dismiss();
                        Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                        Log.e("Error", t.getMessage());
                    } catch (Exception e) {

                    }
                }
            });



    }

    private void makeshipment() {

    }

}
