package com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation;


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

import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Pay_Model;
import com.creativeshare.sals.models.Payment_Result_Model;
import com.creativeshare.sals.models.Shipment_Response_Model;
import com.creativeshare.sals.models.Shipment_Send_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
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
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(num)&&!TextUtils.isEmpty(cvc)&&name.split(" ").length>1){
            pay(name,num,cvc);
        }
        else {
            if(Shipment_Send_Model.getSadad().equals("SADAD")){
                if(!TextUtils.isEmpty(name)){
                    pay(name,"","");
                }
                else {
                    if(TextUtils.isEmpty(name)){
                        edt_name.setError(getResources().getString(R.string.field_req));
                    }
                    if(name.split(" ").length<=1){
                        edt_name.setError(getResources().getString(R.string.name_must_have_space));
                    }
                }

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
            }}
        }
    }

    private void pay(String name, final String num, String cvc) {
        final Pay_Model pay_model=new Pay_Model();
        pay_model.setSource(new Pay_Model.Source());
        pay_model.getSource().setName(name);

        pay_model.getSource().setNumber(num);
        if(Shipment_Send_Model.getSadad().equals("SADAD")){
            pay_model.getSource().setCvc(0);
        }
        else {
        pay_model.getSource().setCvc(Integer.parseInt(cvc));}
        pay_model.setAmount((int)Double.parseDouble(Shipment_Send_Model.getPrice()));
        pay_model.getSource().setMonth(Calendar.getInstance().get(Calendar.MONTH)+1);
        pay_model.getSource().setYear(Calendar.getInstance().get(Calendar.YEAR));
        pay_model.getSource().setType(Shipment_Send_Model.getSadad());

pay_model.setCallback_url("https://www.google.com/");
            //  Log.e("data", Shipment_Send_Model.getDate()+wegights+is_dutiable+Shipment_Send_Model.getTime()+ready_time_gmt_offset+dimension_unit+weight_unit+payment_country_code+Shipment_Send_Model.getFromcountrycode()+Shipment_Send_Model.getCityf()+to_city+to_country_code);
            final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            Api.getService().Payship("Bearer"+" "+ userModel.getToken(), pay_model).enqueue(new Callback<Payment_Result_Model>() {
                @Override
                public void onResponse(Call<Payment_Result_Model> call, Response<Payment_Result_Model> response) {
                    dialog.dismiss();
                    if(response.isSuccessful()){
                        Gson gson = new Gson();
                        String json = gson.toJson(pay_model);
                        //     assert response.body() != null;
                        //  Log.e("price",response.body().getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
                        //  activity.DisplayFragmentComputrizedprice();
                  /*  if (response.body() != null) {
                        Log.e("ss",response.body().toString()+response.raw()+response.headers()+response.body().getData().getGetQuoteResponse().getBkgDetails());
                    }*/
Log.e("model",json+response.body().getAmount()+num.length());
if(response.body().getAmount()!=0.0){
                  makeshipment();}

                    }
                    else {
                        try {
                            Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                            Log.e("Error_code", response.code() + "" + response.errorBody().string()+response.raw().request().body()+response.headers());
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
        HashMap<String, String>  hashMap=new HashMap();
        Log.e("s",Shipment_Send_Model.getWidths().size()-1+"");
        for(int i=0;i<Shipment_Send_Model.getWidths().size();i++){
            hashMap.put("pieces["+i+"][weight]",Shipment_Send_Model.getWegights().get(i));
            hashMap.put("pieces["+i+"][dim_weight]",Shipment_Send_Model.getVolumeweights().get(i));
            hashMap.put("pieces["+i+"][width]",Shipment_Send_Model.getWidths().get(i));
            hashMap.put("pieces["+i+"][height]",Shipment_Send_Model.getHights().get(i));
            hashMap.put("pieces["+i+"][depth]",Shipment_Send_Model.getLengths().get(i));

        }
     /*   hashMap.put("pieces[0][weight]",Shipment_Send_Model.getWegights());
        hashMap.put("pieces[0][dim_weight]",Shipment_Send_Model.getVolumeweights());
        hashMap.put("pieces[0][width]",Shipment_Send_Model.getWidths());
        hashMap.put("pieces[0][height]",Shipment_Send_Model.getHights());
        hashMap.put("pieces[0][depth]",Shipment_Send_Model.getLengths());*/
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
Api.getService().makeshipment("Bearer "+" "+userModel.getToken(),Shipment_Send_Model.getParcel(),userModel.getUser().getFirst_name()+userModel.getUser().getLast_name(),Shipment_Send_Model.getAddreessf(),Shipment_Send_Model.getCityf(),Shipment_Send_Model.getPostalf(),Shipment_Send_Model.getFromcountrycode(),Shipment_Send_Model.getCountryf(),userModel.getUser().getFirst_name()+userModel.getUser().getLast_name(),userModel.getUser().getMobile_number(), PhoneNumberUtil.createInstance(activity).getCountryCodeForRegion(Shipment_Send_Model.getFromcountrycode())+"",userModel.getUser().getEmail(),Shipment_Send_Model.getName(),Shipment_Send_Model.getPhone(),"966",Shipment_Send_Model.getEmailt(),Shipment_Send_Model.getDate(),Shipment_Send_Model.getDesc(),Shipment_Send_Model.getWegights().get(0),Shipment_Send_Model.getAdddresst(),Shipment_Send_Model.getCityt(),Shipment_Send_Model.getPostalt(),"SA","SAUDI ARABIA",Shipment_Send_Model.getName(),Shipment_Send_Model.getWegights().size()+"",hashMap).enqueue(new Callback<Shipment_Response_Model>() {
    @Override
    public void onResponse(Call<Shipment_Response_Model> call, Response<Shipment_Response_Model> response) {
        dialog.dismiss();

        if(response.isSuccessful()){
if(response.body().getPiece()!=null){
    Toast.makeText(activity, R.string.success, Toast.LENGTH_SHORT).show();
//    Log.e("suc",response.body().getPiece()+" "+response.body().getPieces().getPiece().get(response.body().getPieces().getPiece().size()-1).getDepth()+""+response.body().getPieces().getPiece().get(0).getDepth());
activity.display();
}

else {
    Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
    Log.e("ll",response.body().getResponse().getStatus().getCondition().getConditionData()+" ");

}
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
    public void onFailure(Call<Shipment_Response_Model> call, Throwable t) {
        try {
            dialog.dismiss();
            Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
            Log.e("Error", t.getMessage());
        } catch (Exception e) {

        }
    }
});
    }

}
