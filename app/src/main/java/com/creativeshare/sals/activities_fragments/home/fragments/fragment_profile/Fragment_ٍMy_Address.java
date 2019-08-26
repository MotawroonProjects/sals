package com.creativeshare.sals.activities_fragments.home.fragments.fragment_profile;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.adapter.Address_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ٍMy_Address extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
    private FrameLayout fr_searchadress;
    private RecyclerView recaddress;
    private Address_Adapter address_adapter;
    private List<Address_Model.Addresses> addressesList;
    private UserModel userModel;
    private Preferences preferences;
    private ProgressBar progBar;

    public static Fragment_ٍMy_Address newInstance() {
        return new Fragment_ٍMy_Address();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_address, container, false);
        initView(view);
        getadress();
        return view;
    }

    private void getadress() {
        progBar.setVisibility(View.VISIBLE);
        Api.getService().getalladdress("Bearer" + " " + userModel.getToken()).enqueue(new Callback<Address_Model>() {
            @Override
            public void onResponse(Call<Address_Model> call, Response<Address_Model> response) {
                progBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body().getAddresses() != null && response.body().getAddresses().size() > 0) {
                    addressesList.clear();
                    addressesList.addAll(response.body().getAddresses());
                    address_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Address_Model> call, Throwable t) {
                progBar.setVisibility(View.GONE);
                try {
                    Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
                    Log.e("Error ", t.getMessage());

                } catch (Exception e) {

                }
            }
        });
    }

    private void initView(View view) {
        addressesList = new ArrayList<>();
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        fr_searchadress = view.findViewById(R.id.fr_searchadress);
        recaddress = view.findViewById(R.id.rec_address);
        progBar = view.findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        address_adapter = new Address_Adapter(addressesList, activity);
        recaddress.setDrawingCacheEnabled(true);
        recaddress.setItemViewCacheSize(25);
        recaddress.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recaddress.setLayoutManager(new GridLayoutManager(activity, 1));
        recaddress.setAdapter(address_adapter);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        fr_searchadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentAddaddress();

            }
        });
    }

    public void updatedata(Address_Model body) {
        addressesList.clear();
        Log.e("eee",addressesList.size()+"");
        addressesList.addAll(body.getAddresses());

        address_adapter.notifyDataSetChanged();
    }

}