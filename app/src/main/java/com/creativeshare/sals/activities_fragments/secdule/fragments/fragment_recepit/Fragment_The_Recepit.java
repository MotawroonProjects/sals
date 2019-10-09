package com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_recepit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.adapter.Bike_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Move_Data_Model;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Address_Models;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_The_Recepit extends Fragment implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
    private Scedule_Activity activity;
    private String current_lang;
    private LinearLayout ll_search_for_address, ll_search_for_addressto, ll_date,ll_time;
    // private FrameLayout fr_shape1,fr_shape2,fr_shape3;
    private ImageView arrow_search_for_address, arrow1, arrow2, arrow4;
    // im_shape1,im_shape2,im_shape3,
    // private TextView tv_shape1,tv_shape2,tv_shape3;
    private TextView tv_user, tv_addressf, tv_addresst, tv_date,tv_time;
    private String addressf,addreesst,desc,date,time;
    private EditText edt_desc;
    private Button next;
    private RecyclerView rec_bike;
    private List<Bike_Model.Sizes> sizes;
    private Bike_Adapter bike_adapter;
    private Preferences preferences;
    private UserModel userModel;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private int type;

    public static Fragment_The_Recepit newInstance() {
        return new Fragment_The_Recepit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_receipt, container, false);
        initView(view);
        getBike();
        getaddress();
        return view;
    }

    private void initView(View view) {
        sizes = new ArrayList<>();
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        //  fr_shape1=view.findViewById(R.id.fr_shape1);
        //fr_shape2=view.findViewById(R.id.fr_shape2);
        //fr_shape3=view.findViewById(R.id.fr_shape3);
        //im_shape1=view.findViewById(R.id.im_shape1);
        //im_shape2=view.findViewById(R.id.im_shape2);
        //im_shape3=view.findViewById(R.id.im_shape3);
        //tv_shape1=view.findViewById(R.id.tv_shape1);
        //tv_shape2=view.findViewById(R.id.tv_shape2);
        //tv_shape3=view.findViewById(R.id.tv_shape3);
        ll_date = view.findViewById(R.id.ll_date);
        ll_time=view.findViewById(R.id.ll_time);
        tv_user = view.findViewById(R.id.tv_user);
        tv_date = view.findViewById(R.id.tv_date);
        tv_time=view.findViewById(R.id.tv_time);
        tv_addressf = view.findViewById(R.id.tv_address);
        tv_addresst = view.findViewById(R.id.tv_addressto);
        edt_desc=view.findViewById(R.id.edt_desc);
        arrow_search_for_address = view.findViewById(R.id.arrow3);
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2 = view.findViewById(R.id.arrow2);
        arrow4 = view.findViewById(R.id.arrow4);
        rec_bike = view.findViewById(R.id.rec_bike);
        bike_adapter = new Bike_Adapter(sizes, activity);
        rec_bike.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, true));
        rec_bike.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rec_bike.setDrawingCacheEnabled(true);
        rec_bike.setItemViewCacheSize(25);
        rec_bike.setAdapter(bike_adapter);
        ll_search_for_address = view.findViewById(R.id.ll_search_for_address);
        ll_search_for_addressto = view.findViewById(R.id.ll_search_for_addressto);
        if (current_lang.equals("en")) {
            arrow_search_for_address.setRotation(180.0f);
            arrow1.setRotation(180.0f);
            arrow2.setRotation(180.0f);
            arrow4.setRotation(180.0f);
        }
        ll_search_for_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = 0;
                tv_addressf.setError(null);
                tv_date.setError(null);
                tv_user.setError(null);
                tv_time.setError(null);

                activity.DisplayFragmentSearchforaddress();
            }
        });
        ll_search_for_addressto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                activity.DisplayFragmentSearchforaddress();
            }
        });
        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_addressf.setError(null);
                tv_date.setError(null);
                tv_user.setError(null);
                tv_time.setError(null);

                datePickerDialog.show(activity.getFragmentManager(), "");

            }
        });
        /*
fr_shape1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape1.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape1.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));



    }
});
fr_shape2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape2.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape3.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape2.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
        tv_shape3.setTextColor(getResources().getColor(R.color.black));
    }
});
fr_shape3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fr_shape3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape_red));
        fr_shape2.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        fr_shape1.setBackgroundDrawable(getResources().getDrawable(R.drawable.linear_shape));
        im_shape3.setColorFilter(getResources().getColor(R.color.colorPrimary));
        im_shape2.setColorFilter(getResources().getColor(R.color.gray4));
        im_shape1.setColorFilter(getResources().getColor(R.color.gray4));
        tv_shape3.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_shape2.setTextColor(getResources().getColor(R.color.black));
        tv_shape1.setTextColor(getResources().getColor(R.color.black));
    }
});*/
        next = view.findViewById(R.id.bt_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

checkdata();

            }
        });
        ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_time.setError(null);

                tv_addressf.setError(null);
                tv_date.setError(null);
                tv_user.setError(null);
                timePickerDialog.show(activity.getFragmentManager(), "");
            }
        });

        createDatePickerDialog();
        createTimePickerDialog();

    }

    private void checkdata() {
        Common.CloseKeyBoard(activity,edt_desc);

        desc=edt_desc.getText().toString();
        if(!TextUtils.isEmpty(addressf)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(time)){
            edt_desc.setError(null);
            tv_time.setError(null);
            tv_addressf.setError(null);
            tv_date.setError(null);
            tv_user.setError(null);
            addressf=addressf.replaceAll("،","");
            addressf = addressf.replaceAll("\\s(\\d)", "");
            addressf = addressf.replaceAll("(\\d)\\s", "");
            addressf=addressf.replaceAll(",","");
            if(addressf.length()>23){
                addressf=addressf.substring(0,22);
            }

            Log.e("add",addressf);


            Move_Data_Model.setAddreessf(addressf);
           // Move_Data_Model.setAdddresst(addreesst);
            Move_Data_Model.setDate(date);
            Move_Data_Model.setDesc(desc);
            Move_Data_Model.settime(time);
            activity.DisplayFragmentshippingdetilas();

        }
        else {
            if(TextUtils.isEmpty(desc)){
                edt_desc.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(addressf)||TextUtils.isEmpty(date)){
                if(TextUtils.isEmpty(date)){
                    tv_date.setError(getResources().getString(R.string.field_req));
                }
                if(TextUtils.isEmpty(addressf)){
                    tv_addressf.setError(getResources().getString(R.string.field_req));
                }

            }
            if(TextUtils.isEmpty(time)){
                tv_time.setError(getResources().getString(R.string.field_req));
            }

        }
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

    private void getaddress() {
        final Dialog dialog = Common.createProgressDialog(getActivity(), getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().Singleadress("Bearer" + " " + userModel.getToken()).enqueue(new Callback<Address_Models>() {
            @Override
            public void onResponse(Call<Address_Models> call, Response<Address_Models> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    updatedata(response.body());

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    Log.e("Errot_Code", response.code() + " " + response.errorBody()+response.message());
                }
            }

            @Override
            public void onFailure(Call<Address_Models> call, Throwable t) {
                dialog.dismiss();
                Log.e("Erorr", t.getMessage());

//Toast.makeText(getActivity(),getResources().getString(R.string.))
            }
        });

    }

  public void updatedata(Address_Models body) {
      // updatepostalcode(body.getAddress().getLatitude(),body.getAddress().getLongitude());
//getGeoData(body.getAddress().getAddress());
        tv_user.setText(userModel.getUser().getFirst_name() + userModel.getUser().getLast_name());
        addressf=body.getAddress().getAddress();
        //cityf=body.getAddress().getAddress().split(", ")[1];
      //  Log.e("c",cityf);
        if (body != null) {
            if (body.getAddress() != null && body.getAddress().getAddress() != null) {

                tv_addressf.setText(body.getAddress().getAddress());
            }
        }
    }

    private void getBike() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getBike("Bearer" + " " + userModel.getToken(), current_lang).enqueue(new Callback<Bike_Model>() {
            @Override
            public void onResponse(Call<Bike_Model> call, Response<Bike_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getSizes().size() > 0) {
                    sizes.clear();
                    sizes.addAll(response.body().getSizes());
                    bike_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Bike_Model> call, Throwable t) {
                //progBar.setVisibility(View.GONE);
                dialog.dismiss();
                try {
                    Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
                    Log.e("Error ", t.getMessage());

                } catch (Exception e) {

                }
            }
        });

    }

    public void updateaddreess(String body) {
        if (type == 0) {
            tv_addressf.setText(body);
          //  getGeoData(body);
            addressf=body;
        } else if (type == 1) {
            tv_addresst.setText(body);
        }
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
        //Log.e("kkkk", calendar.getTime().getMonth() + "");

        tv_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) < 10 ? "0" + calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : calendar.get(Calendar.DAY_OF_MONTH));
        // date = calendar.get(Calendar.YEAR) + "-" + (calendar.getTime().getMonth()+calendar.getTime().getMonth():calendar.getTime().getMonth()) + "-" + (calendar.getTime().getDay()<10?"0"+calendar.getTime().getDay():calendar.getTime().getDay());
        //Log.e("kkk", date);

    }/*
    private void getGeoData(final String address) {


        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoDatapos(address, current_lang,true, getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        if (response.isSuccessful() && response.body() != null) {


                            if (response.body().getResults().size() > 0) {
                              //  formated_address = response.body().getResults().get(0).getFormatted_address().replace("Unnamed Road,", "");
                                // address.setText(formatedaddress);
                                //tv_location.setText(formated_address);
updatepostalcode(response.body().getResults());
                                //AddMarker(lat, lng);
                                //place_id = response.body().getCandidates().get(0).getPlace_id();
                                //   Log.e("kkk", formatedaddress);
                            }
                        } else {
                            Log.e("error_code", response.errorBody() + " " + response.code()+response.message());

                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceGeocodeData> call, Throwable t) {
                        try {


                            // Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                    }
                });
    }*/
/*
    private void updatepostalcode(List<PlaceGeocodeData.Geocode> address_components) {
        PlaceGeocodeData.Geocode geocode;
        List<PlaceGeocodeData.Address_components> address_components1;
        List<String> typs;
        for(int i=0;i<address_components.size();i++){
            geocode=address_components.get(i);
            address_components1=geocode.getAddress_components();
            for(int j=0;j<address_components1.size();j++){
                typs=address_components1.get(j).getTypes();
                for(int k=0;k<typs.size();k++){
                    if(typs.get(k).equals("postal_code")){
                        postal_code=address_components1.get(j).getLong_name();
                        Log.e("postal",postal_code);
                        Move_Data_Model.setpostalf(postal_code);
                        ;
                    }
                    //Log.e("llll",address_components1.get(j).getLong_name());
                    if(typs.get(k).equals("administrative_area_level_2")||typs.get(k).equals("locality")){
                     cityf=address_components1.get(j).getLong_name();
                     Log.e("c",cityf);
                    }
                    if(typs.get(k).equals("country")){
                        Move_Data_Model.setcode(address_components1.get(j).getShort_name());
                        Move_Data_Model.setcountryf(address_components1.get(j).getLong_name());
                    }
                }
            }
            if(postal_code==null){
                Move_Data_Model.setpostalf("0");
            }
        }
    }

    private void updatepostalcode(double latitude,double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(activity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (addresses != null && addresses.size() > 0) {

            Address address;
            for (int i = 0; i < addresses.size(); i++) {
                address = addresses.get(i);
                if (address.getPostalCode() != null) {
                    postal_code = address.getPostalCode();
                    break;
                }

            }


        }
        Log.e("ss", postal_code+"5"+" "+latitude+" "+longitude);

    }*/
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


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        time = "PT" + calendar.getTime().getHours() + "H" + calendar.getTime().getMinutes() + "M";
        tv_time.setText(time);
    }
}
