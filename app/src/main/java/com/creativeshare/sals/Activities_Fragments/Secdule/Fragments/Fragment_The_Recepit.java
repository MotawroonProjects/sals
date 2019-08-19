package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Adapter.Bike_Adapter;
import com.creativeshare.sals.Adapter.Help_Cat_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.Address_Models;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.models.PlaceGeocodeData;
import com.creativeshare.sals.models.Shipment_Send_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_The_Recepit extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Scedule_Activity activity;
    private String current_lang;
    private LinearLayout ll_search_for_address, ll_search_for_addressto, ll_date;
    // private FrameLayout fr_shape1,fr_shape2,fr_shape3;
    private ImageView arrow_search_for_address, arrow1, arrow2, arrow4;
    // im_shape1,im_shape2,im_shape3,
    // private TextView tv_shape1,tv_shape2,tv_shape3;
    private TextView tv_user, tv_addressf, tv_addresst, tv_date;
    private String addressf,addreesst,desc,date,postal_code,cityf;
    private EditText edt_desc;
    private Button next;
    private RecyclerView rec_bike;
    private List<Bike_Model.Sizes> sizes;
    private Bike_Adapter bike_adapter;
    private Preferences preferences;
    private UserModel userModel;
    private DatePickerDialog datePickerDialog;

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
        tv_user = view.findViewById(R.id.tv_user);
        tv_date = view.findViewById(R.id.tv_date);
        tv_addressf = view.findViewById(R.id.tv_address);
        tv_addresst = view.findViewById(R.id.tv_addressto);
        edt_desc=view.findViewById(R.id.edt_desc);
        arrow_search_for_address = view.findViewById(R.id.arrow3);
        arrow1 = view.findViewById(R.id.arrow1);
        //arrow2 = view.findViewById(R.id.arrow2);
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
          //  arrow2.setRotation(180.0f);
            arrow4.setRotation(180.0f);
        }
        ll_search_for_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = 0;
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
        createDatePickerDialog();


    }

    private void checkdata() {
        desc=edt_desc.getText().toString();
        if(!TextUtils.isEmpty(addressf)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(desc)){
            edt_desc.setError(null);
            Shipment_Send_Model.setAddreessf(addressf);
           // Shipment_Send_Model.setAdddresst(addreesst);
            Shipment_Send_Model.setDate(date);
            Shipment_Send_Model.setDesc(desc);
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

    private void updatedata(Address_Models body) {
       updatepostalcode(body.getAddress().getLatitude(),body.getAddress().getLongitude());
        tv_user.setText(userModel.getUser().getFirst_name() + userModel.getUser().getLast_name());
        cityf=body.getAddress().getAddress().split(", ")[1];
        Log.e("c",cityf);
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

    }
    private void getGeoData(final double lat, final double lng) {

        String location = lat + "," + lng;
        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoData(location, "en", getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        if (response.isSuccessful() && response.body() != null) {


                            if (response.body().getResults().size() > 0) {
                              //  formated_address = response.body().getResults().get(0).getFormatted_address().replace("Unnamed Road,", "");
                                // address.setText(formatedaddress);
                                //tv_location.setText(formated_address);
//updatepostalcode(response.body().getResults().get(0).getAddress_components());
                                //AddMarker(lat, lng);
                                //place_id = response.body().getCandidates().get(0).getPlace_id();
                                //   Log.e("kkk", formatedaddress);
                            }
                        } else {
                            Log.e("error_code", response.errorBody() + " " + response.code());

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
    }

    private void updatepostalcode(double latitude,double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(activity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
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
        Log.e("ss", postal_code+"5");

    }

}
