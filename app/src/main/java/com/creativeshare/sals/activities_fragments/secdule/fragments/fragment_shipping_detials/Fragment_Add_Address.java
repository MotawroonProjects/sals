package com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_shipping_detials;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
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
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.R;

import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.PlaceGeocodeData;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Add_Address extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener, OnMapReadyCallback {

    private Scedule_Activity activity;
    private Home_Activity home_activity;
    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";
    private CoordinatorLayout coordinatorLayout;
    private EditText edt_buildnum, edt_floor, edt_flatnum, edt_desc, edt_type;
    private TextView tv_save,tv_location;
    private SwitchCompat switch_primary;
    private int primary = 0;
    private NestedScrollView layout;
    private String formated_address;
    private double lat, lang;
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int gps_req = 22;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location location;
    private boolean stop = false;
    private float zoom = 15.6f;
    private Marker marker;
    private GoogleMap mMap;
    private Preferences preferences;
    private UserModel userModel;
    private BottomSheetBehavior mBottomSheetBehavior;
    public static Fragment_Add_Address newInstance(int param) {
        Fragment_Add_Address fragment_search_for_address = new Fragment_Add_Address();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, param);
        fragment_search_for_address.setArguments(bundle);
        return fragment_search_for_address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        updateUI();
        initView(view);

        CheckPermission();


        return view;
    }

    private void initView(final View view) {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(getActivity());
        param = getArguments().getInt(Tag);
        if (param == 1) {
            home_activity = (Home_Activity) getActivity();
            Paper.init(home_activity);
        } else {
            activity = (Scedule_Activity) getActivity();
            Paper.init(activity);
        }
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        back_arrow = view.findViewById(R.id.arrow);
      coordinatorLayout = view.findViewById(R.id.coordinator);
        edt_buildnum = view.findViewById(R.id.edt_build_name);
        edt_floor = view.findViewById(R.id.edt_floor);
        edt_flatnum = view.findViewById(R.id.edt_flat_num);
        edt_desc = view.findViewById(R.id.edt_desc);
        edt_type = view.findViewById(R.id.edt_type);
        switch_primary = view.findViewById(R.id.switch_primary);
        tv_location=view.findViewById(R.id.tv_location);
        tv_save = view.findViewById(R.id.tv_save);
        layout    =view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(layout);
      //  coordinatorLayout.scrollTo(0,0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    /*    if(param==1){
            layout.scrollTo(home_activity.getWindow().getWindowManager().getDefaultDisplay().getWidth(),home_activity.getWindow().getWindowManager().getDefaultDisplay().getHeight());

        }
        else {
            layout.scrollTo(activity.getWindow().getWindowManager().getDefaultDisplay().getWidth(),activity.getWindow().getWindowManager().getDefaultDisplay().getHeight());

    }*/
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (param == 1) {
                    home_activity.Back();
                } else {
                    activity.Back();
                }
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
       // coordinatorLayout.scrollTo(0, coordinatorLayout.getScrollY());
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
        Common.CloseKeyBoard(activity,edt_desc);

        String buildnum = edt_buildnum.getText().toString();
        String floor = edt_floor.getText().toString();
        String flatnum = edt_flatnum.getText().toString();
        String desc = edt_desc.getText().toString();
        String address_type = edt_type.getText().toString();
        if (!TextUtils.isEmpty(buildnum) && !TextUtils.isEmpty(floor) && !TextUtils.isEmpty(flatnum) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(address_type) && formated_address != null) {
            addnewaddress(buildnum, floor, flatnum, desc, address_type, formated_address, primary);
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

    private void addnewaddress(String buildnum, String floor, String flatnum, String desc, String address_type, final String formated_address, int primary) {
        final Dialog dialog = Common.createProgressDialog(getActivity(), getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().Addadress("Bearer" + " " + userModel.getToken(), buildnum, floor, flatnum, desc, address_type, formated_address, lat + "", lang + "", primary + "").enqueue(new Callback<Address_Model>() {
            @Override
            public void onResponse(Call<Address_Model> call, Response<Address_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (param == 1) {
                        home_activity.updatedata(response.body());
                        home_activity.Back();
                    } else if (param == 2) {
                        activity.updatedata(formated_address);
                        activity.Back();
                    }
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

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{gps_perm}, gps_req);
        } else {
            initGoogleApiClient();

        }
    }

    private void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.
                Builder(getActivity()).
                addOnConnectionFailedListener(this).
                addConnectionCallbacks(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1255) {
            if (requestCode == Activity.RESULT_OK) {
                startLocationUpdate();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == gps_req && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initGoogleApiClient();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        lat = location.getLatitude();
        lang = location.getLongitude();
        getGeoData(lat, lang);
        //AddMarker(lat, lang);

        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        if (locationRequest != null) ;
        {
            LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(locationCallback);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        intLocationRequest();

    }

    private void intLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(1000 * 60 * 2);
        locationRequest.setInterval(1000 * 60 * 2);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdate();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), 1255);
                        } catch (Exception e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("not available", "not available");
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
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
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lang)));
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
