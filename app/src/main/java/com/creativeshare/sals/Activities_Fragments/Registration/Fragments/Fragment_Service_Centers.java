package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.R;
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
            double x[]={30.7912434,30.7912438};
            double ys[]={30.9991027,30.9991029};
for(int i=0;i<2;i++){
    AddMarker(x[i],ys[i]);
}

        }
    }
    private void AddMarker(double lat, double lng) {




            IconGenerator iconGenerator = new IconGenerator(getActivity());
            iconGenerator.setBackground(null);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.search_map_icon, null);
            iconGenerator.setContentView(view);
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()).draggable(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));


            marker.setPosition(new LatLng(lat, lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_centers, container, false);
        updateUI();
        initview(view);
        return view;
    }

    private void initview(View view) {
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