package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.PlaceGeocodeData;
import com.creativeshare.sals.models.PlaceMapDetailsData;
import com.creativeshare.sals.remote.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    private Scedule_Activity activity;
    private ImageView arrow;// image_pin;
    private LinearLayout ll_back;//ll_map_type;
    private EditText edt_search, edt_floor;
    //private ProgressBar progBar;
    private TextView tv_address,tv_save;//tv_map_type;
   // private FloatingActionButton fab;
    private String current_language;
    private double lat = 0.0, lng = 0.0;
    private String address="",place_id;
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.6f;
    private boolean stop = false;
    private String from="";



    public static Fragment_Map newInstance() {
        Fragment_Map fragment_map = new Fragment_Map();

        return fragment_map;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initView(view);
        return view;
    }



    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

        arrow = view.findViewById(R.id.arrow);

        if (current_language.equals("ar")) {
          arrow.setRotation(180.0f);
        }
        //image_pin = view.findViewById(R.id.image_pin);
        ll_back = view.findViewById(R.id.ll_back);
        edt_search = view.findViewById(R.id.edt_search);
        edt_floor = view.findViewById(R.id.edt_floor);
       // progBar = view.findViewById(R.id.progBar);
        //progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tv_address = view.findViewById(R.id.tv_address);
        tv_save=view.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address!=null){
                    activity.setaddress(address);
                }
            }
        });
       /* fab = view.findViewById(R.id.fab);
        ll_map_type = view.findViewById(R.id.ll_map_type);
        tv_map_type = view.findViewById(R.id.tv_map_type);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(address))
                {
                    Common.CloseKeyBoard(activity,edt_search);
                    String floor = edt_floor.getText().toString().trim();
                    Favourite_location favourite_location = new Favourite_location(place_id,"",floor,address,lat,lng);
                    activity.getAddressFromMapListener(favourite_location,from);
                }
            }
        });*/
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = edt_search.getText().toString();
                    if (!TextUtils.isEmpty(query)) {
                        Search(query);
                        return true;
                    }
                }
                return false;
            }
        });

     /*   ll_map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mMap.getMapType())
                {
                    case GoogleMap.MAP_TYPE_NORMAL:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        tv_map_type.setText(getString(R.string.hybrid));
                        break;

                    case GoogleMap.MAP_TYPE_HYBRID:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        tv_map_type.setText(getString(R.string.terrain));


                        break;
                    case GoogleMap.MAP_TYPE_TERRAIN:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        tv_map_type.setText(getString(R.string.normal));

                        break;
                }
            }
        });*/
/*
        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString(TAG1);
            lat = bundle.getDouble(TAG2);
            lng = bundle.getDouble(TAG3);
            updateUI();
        }*/


    }



    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            //tv_map_type.setText(getString(R.string.normal));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
           // mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.maps));
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            //getGeoData(lat,lng);

            AddMarker(lat, lng,true);



            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {


                    if (!stop)
                    {
                        edt_search.setText("");
                        double lat = mMap.getCameraPosition().target.latitude;
                        double lng = mMap.getCameraPosition().target.longitude;
                        getGeoData(lat,lng);
                    }

                    stop = false;





                }
            });



            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    lat = mMap.getCameraPosition().target.latitude;
                    lng =  mMap.getCameraPosition().target.longitude;
                    AddMarker(lat,lng,true);



                }
            });

        }
    }

  private void Search(String query) {

        //image_pin.setVisibility(View.GONE);
        //progBar.setVisibility(View.VISIBLE);

        String fields = "id,place_id,name,geometry,formatted_address";

        Api.getService("https://maps.googleapis.com/maps/api/")
                .searchOnMap("textquery", query, fields, current_language, getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceMapDetailsData>() {
                    @Override
                    public void onResponse(Call<PlaceMapDetailsData> call, Response<PlaceMapDetailsData> response) {
                        if (response.isSuccessful() && response.body() != null) {

                          //  image_pin.setVisibility(View.VISIBLE);
                          //  progBar.setVisibility(View.GONE);

                            if (response.body().getCandidates().size() > 0) {

                                address = response.body().getCandidates().get(0).getFormatted_address().replace("Unnamed Road,","");
                                //place_id = response.body().getCandidates().get(0).getPlace_id();
                                tv_address.setText(address+"");
                                AddMarker(response.body().getCandidates().get(0).getGeometry().getLocation().getLat(),response.body().getCandidates().get(0).getGeometry().getLocation().getLng(),false);
                            }
                        }
                        else
                        {


                            //progBar.setVisibility(View.GONE);
                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceMapDetailsData> call, Throwable t) {
                        try {


                          //  image_pin.setVisibility(View.VISIBLE);
                         //   progBar.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getGeoData(final double lat, double lng)
    {
       // image_pin.setVisibility(View.GONE);
       // progBar.setVisibility(View.VISIBLE);

        String location = lat+","+lng;
        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoData(location,current_language,getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        if (response.isSuccessful() && response.body() != null) {

                          //  image_pin.setVisibility(View.VISIBLE);
                         //   progBar.setVisibility(View.GONE);

                            if (response.body().getResults().size()>0)
                            {
                                address =response.body().getResults().get(0).getFormatted_address().replace("Unnamed Road,","");
                                place_id = response.body().getResults().get(0).getPlace_id();
                                tv_address.setText(address+"");
                                stop = true;
                                AddMarker(response.body().getResults().get(0).getGeometry().getLocation().getLat(),response.body().getResults().get(0).getGeometry().getLocation().getLng(),true);
                            }
                        }
                        else
                        {

                         //   image_pin.setVisibility(View.VISIBLE);
                          //  progBar.setVisibility(View.GONE);
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


                          //  image_pin.setVisibility(View.VISIBLE);
                          //  progBar.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void AddMarker(double lat, double lng,boolean isMove) {

        this.lat = lat;
        this.lng = lng;

        if (marker == null) {
            IconGenerator iconGenerator = new IconGenerator(activity);
            iconGenerator.setBackground(null);
            View view = LayoutInflater.from(activity).inflate(R.layout.search_map_icon, null);
            iconGenerator.setContentView(view);
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoom));
        } else {
            marker.setPosition(new LatLng(lat, lng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoom));


        }
    }



}
