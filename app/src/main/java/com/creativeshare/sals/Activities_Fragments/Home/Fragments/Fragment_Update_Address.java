package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.Address_Models;
import com.creativeshare.sals.models.PlaceGeocodeData;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Update_Address extends Fragment implements OnMapReadyCallback {
    private Home_Activity home_activity;
    private String current_lang;
    private ImageView back_arrow;
    private int address_id;
    final static private String Tag = "address_id";
    private CoordinatorLayout coordinatorLayout;
    private EditText edt_buildnum, edt_floor, edt_flatnum, edt_desc, edt_type;
    private TextView tv_save,tv_location;
    ;
    private SwitchCompat switch_primary;
    private int primary = 0;
    private String formated_address;
    private double lat, lang;
    private float zoom = 15.6f;
    private Marker marker;
    private GoogleMap mMap;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Update_Address newInstance(int address_id) {
        Fragment_Update_Address fragment_search_for_address = new Fragment_Update_Address();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, address_id);
        fragment_search_for_address.setArguments(bundle);
        return fragment_search_for_address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_address, container, false);
        updateUI();
        initView(view);
       // Log.e("add",address_id+"");
        getaddress(address_id);


        return view;
    }

    private void getaddress(int address_id) {
        final Dialog dialog = Common.createProgressDialog(getActivity(), getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().Singleadress("Bearer" + " " + userModel.getToken(), address_id + "").enqueue(new Callback<Address_Models>() {
            @Override
            public void onResponse(Call<Address_Models> call, Response<Address_Models> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    updatedata(response.body());

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    Log.e("Errot_Code", response.code() + " " + response.errorBody());
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
        if (body.getAddress().getAddress() != null) {
            formated_address=body.getAddress().getAddress();
            tv_location.setText(formated_address);

        }
        if (body.getAddress().getBuilding_number() != null) {
            edt_buildnum.setText(body.getAddress().getBuilding_number());
        }
        if (body.getAddress().getFloor_number() != null) {
            edt_floor.setText(body.getAddress().getFloor_number());
        }
        if (body.getAddress().getFlat_number() != null) {
            edt_flatnum.setText(body.getAddress().getFlat_number());
        }
        if (body.getAddress().getNotes() != null) {
            edt_desc.setText(body.getAddress().getNotes());
        }
        if (body.getAddress().getAddress_type() != null) {
            edt_type.setText(body.getAddress().getAddress_type());
        }
        if (body.getAddress().getIs_primary() == 1) {
            switch_primary.setChecked(true);
        } else {
            switch_primary.setChecked(false);
        }
        lat = body.getAddress().getLatitude();
        lang = body.getAddress().getLongitude();
        AddMarker(lat, lang);

    }

    private void initView(View view) {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(getActivity());
        address_id = getArguments().getInt(Tag);

        home_activity = (Home_Activity) getActivity();
        Paper.init(home_activity);

        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        back_arrow = view.findViewById(R.id.arrow);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        edt_buildnum = view.findViewById(R.id.edt_build_name);
        edt_floor = view.findViewById(R.id.edt_floor);
        edt_flatnum = view.findViewById(R.id.edt_flat_num);
        edt_desc = view.findViewById(R.id.edt_desc);
        edt_type = view.findViewById(R.id.edt_type);
        switch_primary = view.findViewById(R.id.switch_primary);
        tv_location = view.findViewById(R.id.tv_location);

        tv_save = view.findViewById(R.id.tv_save);

        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_activity.Back();

            }
        });
        switch_primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_primary.isChecked()) {
                    primary = 1;
                } else {
                    primary = 0;
                }
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });
   //     coordinatorLayout.scrollTo(0, coordinatorLayout.getScrollY());
        //scrollView.fullScroll(View.FOCUS_UP);
        //scrollView.smoothScrollTo(0,0);
        // scrollView.fullScroll(View.FOCUS_DOWN);
        //scrollView.scrollTo(0,0);
        //scrollView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        // nestedScrollView.dispatchNestedPreScroll(0, toolbarHeight, null, null);
        // nestedScrollView.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -toolbarHeight});
        //scrollView.scrollTo(0, scrollView.getBottom());
    }

    private void checkdata() {
        String buildnum = edt_buildnum.getText().toString();
        String floor = edt_floor.getText().toString();
        String flatnum = edt_flatnum.getText().toString();
        String desc = edt_desc.getText().toString();
        String address_type = edt_type.getText().toString();
        if (!TextUtils.isEmpty(buildnum) && !TextUtils.isEmpty(floor) && !TextUtils.isEmpty(flatnum) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(address_type) && formated_address != null) {
            updateaddress(buildnum, floor, flatnum, desc, address_type, formated_address, primary);
        } else {
            if (TextUtils.isEmpty(buildnum)) {
                edt_buildnum.setError(getResources().getString(R.string.field_req));
            }
            if (TextUtils.isEmpty(floor)) {
                edt_floor.setError(getResources().getString(R.string.field_req));
            }
            if (TextUtils.isEmpty(flatnum)) {
                edt_flatnum.setError(getResources().getString(R.string.field_req));
            }
            if (TextUtils.isEmpty(desc)) {
                edt_desc.setError(getResources().getString(R.string.field_req));
            }
            if (TextUtils.isEmpty(address_type)) {
                edt_type.setError(getResources().getString(R.string.field_req));
            }
            if (formated_address == null) {
                Toast.makeText(getActivity(), getResources().getString(R.string.addres) + getResources().getString(R.string.field_req), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateaddress(String buildnum, String floor, String flatnum, String desc, String address_type, String formated_address, int primary) {
        final Dialog dialog = Common.createProgressDialog(getActivity(), getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().updateadress("Bearer" + " " + userModel.getToken(), buildnum, floor, flatnum, desc, address_type, formated_address, lat + "", lang + "", primary + "",address_id+"").enqueue(new Callback<Address_Model>() {
            @Override
            public void onResponse(Call<Address_Model> call, Response<Address_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    home_activity.updatedata(response.body());
                    home_activity.Back();

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    Log.e("Errot_Code", response.code() + " " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Address_Model> call, Throwable t) {
                dialog.dismiss();
                Log.e("Erorr", t.getMessage());

//Toast.makeText(getActivity(),getResources().getString(R.string.))
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
//    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.maps));
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    lat = latLng.latitude;
                    lang = latLng.longitude;
                    //  Log.e("nnn",lat+"  "+lng);
                    getGeoData(lat, lang);
                    // AddMarker(lat, lang);
                }
            });


        }
    }

    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        fragment.getMapAsync(this);

    }

    private void AddMarker(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
        if (marker == null) {
            IconGenerator iconGenerator = new IconGenerator(getActivity());
            iconGenerator.setBackground(null);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.search_map_icon, null);
            iconGenerator.setContentView(view);
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lang)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()).draggable(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), zoom));
        } else {
            marker.setPosition(new LatLng(lat, lang));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), zoom));
        }

    }

    private void getGeoData(final double lat, final double lng) {

        String location = lat + "," + lng;
        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoData(location, current_lang, getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        if (response.isSuccessful() && response.body() != null) {


                            if (response.body().getResults().size() > 0) {
                                formated_address = response.body().getResults().get(0).getFormatted_address().replace("Unnamed Road,", "");
                                // address.setText(formatedaddress);
                                tv_location.setText(formated_address);

                                AddMarker(lat, lng);
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


}
