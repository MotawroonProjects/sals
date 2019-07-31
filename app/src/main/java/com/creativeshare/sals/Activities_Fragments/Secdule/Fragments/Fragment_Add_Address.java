package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Add_Address extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener, OnMapReadyCallback {
    private Scedule_Activity activity;
    private Home_Activity home_activity;
    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";
    private CoordinatorLayout coordinatorLayout;
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

    private void initView(View view) {

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

        coordinatorLayout.scrollTo(0, coordinatorLayout.getScrollY());
        //scrollView.fullScroll(View.FOCUS_UP);
        //scrollView.smoothScrollTo(0,0);
        // scrollView.fullScroll(View.FOCUS_DOWN);
        //scrollView.scrollTo(0,0);
        //scrollView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        // nestedScrollView.dispatchNestedPreScroll(0, toolbarHeight, null, null);
        // nestedScrollView.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -toolbarHeight});
        //scrollView.scrollTo(0, scrollView.getBottom());
    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(home_activity, gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{gps_perm}, gps_req);
        } else {
            initGoogleApiClient();

        }
    }

    private void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.
                Builder(home_activity).
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
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        if (locationRequest != null) ;
        {
            LocationServices.getFusedLocationProviderClient(home_activity).removeLocationUpdates(locationCallback);
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
                            status.startResolutionForResult(activity, 1255);
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
        LocationServices.getFusedLocationProviderClient(home_activity).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
if(googleMap!=null){
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
            AddMarker(lat, lang);
        }
    });

}
    }
    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        fragment.getMapAsync(this);

    }
    private void AddMarker(double lat, double lang) {
this.lat=lat;
this.lang=lang;
if(marker==null){
    IconGenerator iconGenerator=new IconGenerator(activity);
    iconGenerator.setBackground(null);
    View view=LayoutInflater.from(activity).inflate(R.layout.search_map_icon,null);
    iconGenerator.setContentView(view);
    marker=mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lang)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(),iconGenerator.getAnchorV()).draggable(true));
mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lang),zoom));
}
else {
    marker.setPosition(new LatLng(lat,lang));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lang),zoom));
}
    }
}
