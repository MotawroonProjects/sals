package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Adapter.Spinner_City_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.CityModel;
import com.creativeshare.sals.models.Computrized_Model;
import com.creativeshare.sals.models.Dementions_Model;
import com.creativeshare.sals.models.Quote_Model;
import com.creativeshare.sals.models.Shipment_Send_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.creativeshare.sals.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Shipping_Detials extends Fragment {
    private Scedule_Activity activity;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private Button next, bt_Shipping_dimensions, bt_incremental, bt_decremantal;
    private FrameLayout fr_document, fr_parcel;
    private ImageView im_document, im_parcel,im_map;
    private TextView tv_document, tv_parcel,tv_Quantity;
    private EditText edt_desc,edt_weight,edt_name,edt_phone,edt_address,edt_email;
    private List<CityModel.Cities> cityModelList;
    private Spinner spinner_city_to,spinner_city_from;
    private Spinner_City_Adapter city_adapter;
    private int quantity = 1;
 private List<String> wegights,widths,hights,lengths,volumeweights;
    private String is_dutiable = "0", ready_time_gmt_offset = "+00:00", dimension_unit = "CM", weight_unit = "KG", payment_country_code = "SA",  postal_codef,cityf,to_city, to_country_code = "SA";
private String parcel="0";
    public static Fragment_Shipping_Detials newInstance() {
        return new Fragment_Shipping_Detials();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping_details, container, false);
        initView(view);
        getCities();
        return view;
    }

    private void initView(View view) {
        cityModelList=new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        wegights=new ArrayList<>();
        lengths=new ArrayList<>();
        widths=new ArrayList<>();
        hights=new ArrayList<>();
        volumeweights=new ArrayList<>();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        bt_incremental = view.findViewById(R.id.increment);
        bt_decremantal = view.findViewById(R.id.decrement);
        tv_Quantity = view.findViewById(R.id.tv_quantity);
        fr_document = view.findViewById(R.id.fr_document);
        fr_parcel = view.findViewById(R.id.fr_parcel);
        im_map = view.findViewById(R.id.im_map);
        im_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentMap();
            }
        });
        im_document=view.findViewById(R.id.im_document);
        im_parcel = view.findViewById(R.id.im_parcel);
        tv_document = view.findViewById(R.id.tv_document);
        tv_parcel = view.findViewById(R.id.tv_parcel);
        edt_desc = view.findViewById(R.id.edt_desc);
        edt_name=view.findViewById(R.id.edt_name);
        edt_phone=view.findViewById(R.id.edt_phone);
        edt_address=view.findViewById(R.id.edt_address);
        edt_weight=view.findViewById(R.id.edt_weight);
        edt_email=view.findViewById(R.id.edt_email);
        bt_Shipping_dimensions = view.findViewById(R.id.bt_shipping_dimensions);
        next = view.findViewById(R.id.bt_next);
        spinner_city_to = view.findViewById(R.id.sp_cityto);
        spinner_city_from= view.findViewById(R.id.sp_cityfrom);
        city_adapter = new Spinner_City_Adapter(activity, cityModelList);
        spinner_city_to.setAdapter(city_adapter);
        spinner_city_from.setAdapter(city_adapter);
        spinner_city_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    cityf = "";
                } else {
                    cityf = cityModelList.get(position).getEn_name();
                    // Shipment_Send_Model.setcityt(to_city);
                    Shipment_Send_Model.setpostalf(cityModelList.get(position).getPostal_code());
                    if(current_lang.equals("en")){
                        Shipment_Send_Model.setCityf(cityModelList.get(position).getEn_name());
                    }
                    else {
                        Shipment_Send_Model.setCityf(cityModelList.get(position).getAr_name());

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_city_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    to_city = "";
                } else {
                    to_city = cityModelList.get(position).getEn_name();
                   // Shipment_Send_Model.setcityt(to_city);
                    Shipment_Send_Model.setpostalt(cityModelList.get(position).getPostal_code());
                    if(current_lang.equals("en")){
                Shipment_Send_Model.setcityt(cityModelList.get(position).getEn_name());
                    }
                    else {
                       Shipment_Send_Model.setcityt(cityModelList.get(position).getAr_name());

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt_incremental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity += 1;
                tv_Quantity.setText(quantity + "");
            }
        });
        bt_decremantal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity -= 1;
                    tv_Quantity.setText(quantity + "");
                }
            }
        });
        fr_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parcel="0";
                fr_document.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.white));
                im_document.setColorFilter(getResources().getColor(R.color.white));
                im_parcel.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tv_document.setTextColor(getResources().getColor(R.color.white));
                tv_parcel.setTextColor(getResources().getColor(R.color.colorPrimary));
                bt_Shipping_dimensions.setVisibility(View.GONE);
                edt_desc.setVisibility(View.GONE);
            }
        });
        fr_parcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parcel="1";

                fr_document.setBackgroundColor(getResources().getColor(R.color.white));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                im_document.setColorFilter(getResources().getColor(R.color.colorPrimary));
                im_parcel.setColorFilter(getResources().getColor(R.color.white));
                tv_document.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_parcel.setTextColor(getResources().getColor(R.color.white));
                bt_Shipping_dimensions.setVisibility(View.VISIBLE);
                edt_desc.setVisibility(View.GONE);
            }
        });
        bt_Shipping_dimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentShippingDimentions();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
               // activity.DisplayFragmentdelivrychooser();
            }
        });

    }
    private void getCities() {

        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .getCity("Bearer"+" "+ userModel.getToken(), current_lang)
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                cityModelList.clear();
                                cityModelList.add(new CityModel.Cities("إختر", "Choose"));
                                cityModelList.addAll(response.body().getCities());
                                city_adapter.notifyDataSetChanged();

                            }
                        } else {
                            try {
                                Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                                Log.e("Error_code", response.code() + "" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });

    }
    private void checkdata() {
        if(widths.size()!=0||parcel.equals("0")){
        String weight=edt_weight.getText().toString();
        String name=edt_name.getText().toString();
        String phone=edt_phone.getText().toString();
        String address=edt_address.getText().toString();
        String email=edt_email.getText().toString();
        if ( TextUtils.isEmpty(to_city)||TextUtils.isEmpty(cityf) ||TextUtils.isEmpty(weight)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(address)||TextUtils.isEmpty(email)||! Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            if(TextUtils.isEmpty(weight)){
                edt_weight.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(to_city)||TextUtils.isEmpty(cityf)){
                Common.CreateSignAlertDialog(activity,getResources().getString(R.string.add_city));
            }
            if(TextUtils.isEmpty(name)){
                edt_name.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(phone)){
                edt_phone.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(address)){
                edt_address.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(email)){
                edt_email.setError(getResources().getString(R.string.field_req));
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edt_email.setError(getResources().getString(R.string.error_Email));
            }

        }
        else {
            List<String> wegights=new ArrayList<>();
            edt_weight.setError(null);
            //tv_date.setError(null);
            //tv_time.setError(null);
            int loop;
            if(parcel.equals("1")){
                if(quantity>widths.size()){
                    loop=quantity;
                }
                else {
                loop=widths.size();
            }}
            else {
                loop=quantity;
            }
            for(int i=0;i<loop;i++){
                wegights.add(weight);
if(parcel.equals("0")){
                widths.add("0");
                hights.add("0");
                lengths.add("0");
                volumeweights.add("0");
            }}
          //  Computrized_Model.setQuantity(quantity+"");
          //  Computrized_Model.setWeight(weight);
            Shipment_Send_Model.setWegights(wegights);
            if(parcel.equals("1")){
                if(quantity>widths.size()){
                    int ind=widths.size()-1;
                    for(int i=widths.size()-1;i<quantity;i++){

                        widths.add(widths.get(ind)+"");
                        hights.add(hights.get(ind)+"");
                        lengths.add(lengths.get(ind)+"");
                        volumeweights.add(volumeweights.get(ind)+"");
                    }
                }


            }
            Shipment_Send_Model.setWidths(widths);
            Shipment_Send_Model.setHights(hights);
            Shipment_Send_Model.setLengths(lengths);
            Shipment_Send_Model.setVolumeweights(volumeweights);
            Shipment_Send_Model.setParcel(parcel);
            Shipment_Send_Model.setemailt(email);
            Shipment_Send_Model.setName(name);
            Shipment_Send_Model.setcountryf("");
           // Shipment_Send_Model.setcityt(to_city);
            Shipment_Send_Model.setPhone(phone);
            Shipment_Send_Model.setAdddresst(address);
Shipment_Send_Model.setcode("SA");
            getQoute(wegights);

        }}
        else {
            Common.CreateSignAlertDialog(activity,getResources().getString(R.string.demintion_for_eacj_piece));
        }
    }

    private void getQoute(List<String> wegights) {
      //  Log.e("data", Shipment_Send_Model.getDate()+wegights+is_dutiable+Shipment_Send_Model.getTime()+ready_time_gmt_offset+dimension_unit+weight_unit+payment_country_code+Shipment_Send_Model.getFromcountrycode()+Shipment_Send_Model.getCityf()+to_city+to_country_code);
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().get_quote("Bearer"+" "+ userModel.getToken(), Shipment_Send_Model.getDate(),wegights,is_dutiable,Shipment_Send_Model.getTime(),ready_time_gmt_offset,dimension_unit,weight_unit,payment_country_code,Shipment_Send_Model.getFromcountrycode(),Shipment_Send_Model.getCityf(),to_city,to_country_code).enqueue(new Callback<Quote_Model>() {
            @Override
            public void onResponse(Call<Quote_Model> call, Response<Quote_Model> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    //     assert response.body() != null;
                    //  Log.e("price",response.body().getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
                    //  activity.DisplayFragmentComputrizedprice();
                  /*  if (response.body() != null) {
                        Log.e("ss",response.body().toString()+response.raw()+response.headers()+response.body().getData().getGetQuoteResponse().getBkgDetails());
                    }*/
                    if(response.body().getData().getGetQuoteResponse().getBkgDetails()==null){
//Log.e("res",response.body().getData().getResponse().getStatus().getCondition().getConditionData());
                    }
                    else {
                    adddata(response.body());}
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
            public void onFailure(Call<Quote_Model> call, Throwable t) {
                try {
                    dialog.dismiss();
                    Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage());
                } catch (Exception e) {

                }
            }
        });
    }
    public void adddeminssion(Dementions_Model dementions_model) {
       // Log.e("de",dementions_model.getGrossweight()+"");
        for(int i=0;i<quantity;i++){

            widths.add(dementions_model.getWidth()+"");
            hights.add(dementions_model.getHight()+"");
            lengths.add(dementions_model.getLength()+"");
            volumeweights.add(dementions_model.getVoulumeweight()+"");
        }


    }
    private void adddata(Quote_Model body) {
        Shipment_Send_Model.setPrice(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
        Shipment_Send_Model.setDay_number(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getTotalTransitDays());

        Computrized_Model.setTime(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getDeliveryTime());
        activity.DisplayFragmentdelivrychooser();

    }

    public void setaddressto(String address) {
        edt_address.setText(address);
    }
}
