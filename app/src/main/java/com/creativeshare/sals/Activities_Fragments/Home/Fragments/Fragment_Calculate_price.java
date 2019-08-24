package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Adapter.Spinner_City_Adapter;
import com.creativeshare.sals.Adapter.Spinner_Country_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.CityModel;
import com.creativeshare.sals.models.Computrized_Model;
import com.creativeshare.sals.models.Country_Model;
import com.creativeshare.sals.models.Quote_Array_Model;
import com.creativeshare.sals.models.Quote_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.creativeshare.sals.tags.Tags;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Calculate_price extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Home_Activity activity;
    private String current_lang;
    private Preferences preferences;
    private UserModel userModel;
    private ImageView back_arrow;
    private FrameLayout fr_document, fr_parcel;
    private ImageView im_document, im_parcel;
    private TextView tv_document, tv_parcel, tv_date, tv_time, tv_Quantity;
    private Button bt_claculate, bt_Shipping_dimensions, bt_incremental, bt_decremantal;
    private EditText edt_desc, edt_weight;
    private LinearLayout ll_time, ll_date;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Spinner spinner_city_from, spinner_country_from, spinner_city_to, spinner_country_to;

    private String date;
    private String time;
    private List<CityModel.Cities> cityModelList;
    private Spinner_City_Adapter city_adapter;
    private List<Country_Model.Countries> countriesList;
    private Spinner_Country_Adapter country_adapter;
    private int quantity = 1;
    private String is_dutiable = "0", ready_time_gmt_offset = "+00:00", dimension_unit = "CM", weight_unit = "KG", payment_country_code = "SA", from_country_code = "SA", from_city, to_city, to_country_code = "SA";

    public static Fragment_Calculate_price newInstance() {
        return new Fragment_Calculate_price();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_price, container, false);
        initView(view);
        //   getCountry();
        getCities();
        return view;
    }

    private void initView(View view) {
        cityModelList = new ArrayList<>();
        countriesList = new ArrayList<>();
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        fr_document = view.findViewById(R.id.fr_document);
        fr_parcel = view.findViewById(R.id.fr_parcel);
        im_document = view.findViewById(R.id.im_document);
        im_parcel = view.findViewById(R.id.im_parcel);
        tv_document = view.findViewById(R.id.tv_document);
        tv_parcel = view.findViewById(R.id.tv_parcel);
        bt_claculate = view.findViewById(R.id.bt_claculate);
        ll_time = view.findViewById(R.id.ll_time);
        ll_date = view.findViewById(R.id.ll_date);
        edt_desc = view.findViewById(R.id.edt_desc);
        bt_incremental = view.findViewById(R.id.increment);
        bt_decremantal = view.findViewById(R.id.decrement);
        bt_Shipping_dimensions = view.findViewById(R.id.bt_shipping_dimensions);
        tv_date = view.findViewById(R.id.tv_date);
        tv_time = view.findViewById(R.id.tv_time);
        tv_Quantity = view.findViewById(R.id.tv_quantity);
        spinner_country_from = view.findViewById(R.id.sp_countryfrom);
        spinner_city_from = view.findViewById(R.id.sp_cityfrom);
        spinner_country_to = view.findViewById(R.id.sp_countryto);
        spinner_city_to = view.findViewById(R.id.sp_cityto);
        city_adapter = new Spinner_City_Adapter(activity, cityModelList);
        country_adapter = new Spinner_Country_Adapter(activity, countriesList);
        spinner_city_to.setAdapter(city_adapter);
        spinner_city_from.setAdapter(city_adapter);
        spinner_country_from.setAdapter(country_adapter);
        spinner_country_to.setAdapter(country_adapter);
        edt_weight = view.findViewById(R.id.edt_weight);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }

        spinner_city_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    from_city = "";
                } else {
                    from_city = cityModelList.get(position).getEn_name();
                    if(current_lang.equals("en")){
                        Computrized_Model.setCity_From(cityModelList.get(position).getEn_name());
                    }
                    else {
                        Computrized_Model.setCity_From(cityModelList.get(position).getAr_name());

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
                    if(current_lang.equals("en")){
                        Computrized_Model.setCity_to(cityModelList.get(position).getEn_name());
                    }
                    else {
                        Computrized_Model.setCity_to(cityModelList.get(position).getAr_name());

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      /*  spinner_country_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position ==0)
                {
                    from_country_code ="";
                }else
                {
                    from_country_code = countriesList.get(position).getCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
       /* spinner_country_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position ==0)
                {
                   to_country_code ="";
                }else
                {
                    to_country_code = countriesList.get(position).getCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
                fr_document.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.white));
                im_document.setColorFilter(getResources().getColor(R.color.white));
                im_parcel.setColorFilter(getResources().getColor(R.color.colorPrimary));
                tv_document.setTextColor(getResources().getColor(R.color.white));
                tv_parcel.setTextColor(getResources().getColor(R.color.colorPrimary));
                //  bt_Shipping_dimensions.setVisibility(View.GONE);
                //edt_desc.setVisibility(View.GONE);
                Computrized_Model.setType(getResources().getString(R.string.documents));
            }
        });
        fr_parcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr_document.setBackgroundColor(getResources().getColor(R.color.white));
                fr_parcel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                im_document.setColorFilter(getResources().getColor(R.color.colorPrimary));
                im_parcel.setColorFilter(getResources().getColor(R.color.white));
                tv_document.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_parcel.setTextColor(getResources().getColor(R.color.white));
                //bt_Shipping_dimensions.setVisibility(View.VISIBLE);
                //edt_desc.setVisibility(View.VISIBLE);
                Computrized_Model.setType(getResources().getString(R.string.parcels));

            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        bt_claculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();

               //
            }
        });
        bt_Shipping_dimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkdata();
                // activity.DisplayFragmentShippingDimentions();
            }
        });
        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(activity.getFragmentManager(), "");
            }
        });
        ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show(activity.getFragmentManager(), "");
            }
        });

        createDatePickerDialog();
        createTimePickerDialog();

    }

    private void checkdata() {
        String weight=edt_weight.getText().toString();
        if (TextUtils.isEmpty(from_city) || TextUtils.isEmpty(to_city) ||TextUtils.isEmpty(weight)||TextUtils.isEmpty(date)||TextUtils.isEmpty(time)){
            if(TextUtils.isEmpty(from_city)|| TextUtils.isEmpty(to_city) ){
                Toast.makeText(activity,getResources().getString(R.string.add_city),Toast.LENGTH_LONG).show();
            }
            if(TextUtils.isEmpty(weight)){
                edt_weight.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(date)){
                tv_date.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(time)){
                tv_time.setError(getResources().getString(R.string.field_req));
            }
        }
        else {
            List<String> wegights=new ArrayList<>();
            edt_weight.setError(null);
            tv_date.setError(null);
            tv_time.setError(null);
            for(int i=0;i<quantity;i++){
                wegights.add(weight);
            }
            Computrized_Model.setQuantity(quantity+"");
Computrized_Model.setWeight(weight);
            getQoute(wegights);

        }
    }

    private void getQoute(final List<String> wegights) {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().get_quote("Bearer"+" "+ userModel.getToken(),date,wegights,is_dutiable,time,ready_time_gmt_offset,dimension_unit,weight_unit,payment_country_code,from_country_code,from_city,to_city,to_country_code).enqueue(new Callback<Quote_Model>() {
            @Override
            public void onResponse(Call<Quote_Model> call, Response<Quote_Model> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
               //     assert response.body() != null;
                 //  Log.e("price",response.body().getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
                  //  activity.DisplayFragmentComputrizedprice();
try {
    if(response.body().getData().getGetQuoteResponse()!=null){
    adddata(response.body());}
    else {
       Toast.makeText(activity,getResources().getString(R.string.error_data),Toast.LENGTH_LONG).show();
    }

}
catch (Exception e){

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
            public void onFailure(Call<Quote_Model> call, Throwable t) {
                try {
                    dialog.dismiss();
                    getQoute2(wegights);
                    //Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                    //Log.e("Error", t.getMessage());
                } catch (Exception e) {

                }
            }
        });
    }
    private void getQoute2(List<String> wegights) {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().get_quote2("Bearer"+" "+ userModel.getToken(),date,wegights,is_dutiable,time,ready_time_gmt_offset,dimension_unit,weight_unit,payment_country_code,from_country_code,from_city,to_city,to_country_code).enqueue(new Callback<Quote_Array_Model>() {
            @Override
            public void onResponse(Call<Quote_Array_Model> call, Response<Quote_Array_Model> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    //     assert response.body() != null;
                    //  Log.e("price",response.body().getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
                    //  activity.DisplayFragmentComputrizedprice();

                    adddata2(response.body());

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
            public void onFailure(Call<Quote_Array_Model> call, Throwable t) {
                try {
                    dialog.dismiss();

                    //Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage());
                } catch (Exception e) {

                }
            }
        });
    }

    private void adddata2(Quote_Array_Model body) {
        Computrized_Model.setPrice(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().get(0).getWeightCharge());
        Computrized_Model.setDay_number(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().get(0).getTotalTransitDays());

        Computrized_Model.setTime(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().get(0).getDeliveryTime());
        activity.DisplayFragmentComputrizedprice();
    }

    private void adddata(Quote_Model body) {
        Computrized_Model.setPrice(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getWeightCharge());
        Computrized_Model.setDay_number(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getTotalTransitDays());

        Computrized_Model.setTime(body.getData().getGetQuoteResponse().getBkgDetails().getQtdShp().getDeliveryTime());
        activity.DisplayFragmentComputrizedprice();

    }

    private void createTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        // datePickerDialog.setOkText(getString(R.string.select));
        //datePickerDialog.setCancelText(getString(R.string.cancel));
        timePickerDialog.setLocale(new Locale(current_lang));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setMinTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    private void createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        // datePickerDialog.setOkText(getString(R.string.select));
        //datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setLocale(new Locale(current_lang));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear + 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        // order_time_calender.set(Calendar.YEAR,year);
        //order_time_calender.set(Calendar.MONTH,monthOfYear);
        //order_time_calender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        Log.e("kkkk", calendar.getTime().getMonth() + "");

        tv_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
date=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)<10?"0"+calendar.get(Calendar.MONTH):calendar.get(Calendar.MONTH))+"-"+(calendar.get(Calendar.DAY_OF_MONTH)<10?"0"+calendar.get(Calendar.DAY_OF_MONTH):calendar.get(Calendar.DAY_OF_MONTH));
       // date = calendar.get(Calendar.YEAR) + "-" + (calendar.getTime().getMonth()+calendar.getTime().getMonth():calendar.getTime().getMonth()) + "-" + (calendar.getTime().getDay()<10?"0"+calendar.getTime().getDay():calendar.getTime().getDay());
Log.e("kkk",date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        time = "PT" + calendar.getTime().getHours() + "H" + calendar.getTime().getMinutes() + "M";
        tv_time.setText(time);

        //time = calendar.getTimeInMillis();
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

    private void getCountry() {

        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .getCoutry("Bearer"+" " + userModel.getToken(), current_lang)
                .enqueue(new Callback<Country_Model>() {
                    @Override
                    public void onResponse(Call<Country_Model> call, Response<Country_Model> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                countriesList.clear();
                                countriesList.add(new Country_Model.Countries("Choose", "إختر"));
                                countriesList.addAll(response.body().getCountries());
                                country_adapter.notifyDataSetChanged();

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
                    public void onFailure(Call<Country_Model> call, Throwable t) {
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
