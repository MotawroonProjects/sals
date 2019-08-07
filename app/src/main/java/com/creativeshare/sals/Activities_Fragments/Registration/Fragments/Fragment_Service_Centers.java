package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Sercvices_Centers;
import com.creativeshare.sals.remote.Api;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Service_Centers extends Fragment implements OnMapReadyCallback {
    private Register_Activity register_activity;
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
    private int param;
    final static private String Tag = "chec_activity";
    private float zoom = 15.6f;

    private Marker marker;
    private GoogleMap mMap;
    private List<Sercvices_Centers.Centers> centers;
    public static Fragment_Service_Centers newInstance(int param) {

        Fragment_Service_Centers fragment_service_centers = new Fragment_Service_Centers();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, param);
        fragment_service_centers.setArguments(bundle);
        return fragment_service_centers;
    }
    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            //   mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.maps));
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);

            // AddMarker();


        }
    }
    private void AddMarker() {




        IconGenerator iconGenerator = new IconGenerator(getActivity());
        iconGenerator.setBackground(null);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.search_map_icon, null);
        iconGenerator.setContentView(view);
        iconGenerator.setBackground(null);
        //   iconGenerator.setContentView(view);

        //  LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < centers.size(); i++) {
            //   LatLng ll = new LatLng(x[i], ys[i]);

            // bld.include(ll);
            //Log.e("dd", x[i] + "");
            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(centers.get(i).getLatitude(), centers.get(i).getLongitude()))

                    .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()));

            //  builder.include(marker[i].getPosition());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(centers.get(i).getLatitude(),centers.get(i).getLongitude()),zoom));


        }

        // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(),200);

        // mMap.animateCamera(cameraUpdate);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_centers, container, false);
        updateUI();
        initview(view);
        getservicescenters();
        return view;
    }

    private void getservicescenters() {
        Api.getService().getservicescenter(current_lang).enqueue(new Callback<Sercvices_Centers>() {
            @Override
            public void onResponse(Call<Sercvices_Centers> call, Response<Sercvices_Centers> response) {
                if(response.isSuccessful()){
                    centers.clear();
                    if(response.body()!=null&&response.body().getCenters()!=null&&response.body().getCenters().size()>0){
                        centers.addAll(response.body().getCenters());
                        AddMarker();
                    }
                }
            }

            @Override
            public void onFailure(Call<Sercvices_Centers> call, Throwable t) {

            }
        });
    }

    private void initview(View view) {
        centers=new ArrayList<>();
        param = getArguments().getInt(Tag);
        if (param == 1) {
            register_activity = (Register_Activity) getActivity();
            Paper.init(register_activity);
        } else {
            activity = (Home_Activity) getActivity();
            Paper.init(activity);
        }
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (param == 1) {
                            register_activity.Back();
                        } else {
                            activity.Back();
                        }
                    }
                });
    }


}