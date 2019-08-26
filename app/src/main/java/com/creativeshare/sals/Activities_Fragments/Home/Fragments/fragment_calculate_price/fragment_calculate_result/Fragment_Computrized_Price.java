package com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_calculate_price.fragment_calculate_result;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Computrized_Model;
import com.creativeshare.sals.models.Prectage_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Computrized_Price extends Fragment {
    private Home_Activity activity;
    private String current_lang;
    private ImageView back_arrow, im_additionalservices;
    private TextView tv_document, tv_cityf, tv_cityt, tv_day, tv_num, tv_price, tv_time,tv_total_pricesals;
    private LinearLayout ll_next;
private Preferences preferences;
private UserModel userModel;
    public static Fragment_Computrized_Price newInstance() {
        return new Fragment_Computrized_Price();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computerized_price, container, false);
        initView(view);
        getappcommission();
       // applydata(response.body());
        return view;
    }

    private void applydata(Prectage_Model body) {
        double price=Double.parseDouble(body.getRate());
        tv_document.setText(Computrized_Model.getType());
        tv_cityf.setText(Computrized_Model.getCity_From());
        tv_cityt.setText(Computrized_Model.getCity_to());
        tv_num.setText(Computrized_Model.getQuantity() + getResources().getString(R.string.pieces) + Computrized_Model.getWeight() + getResources().getString(R.string.kg));
        tv_day.setText(getResources().getString(R.string.Delivery)+" "+Computrized_Model.getDay_number()+getResources().getString(R.string.days));
        tv_price.setText(getResources().getString(R.string.from_dhl)+" "+Computrized_Model.getPrice() + getResources().getString(R.string.ryal));
        tv_total_pricesals.setText(getResources().getString(R.string.from_sals)+" "+(Double.parseDouble(Computrized_Model.getPrice())-(Double.parseDouble(Computrized_Model.getPrice())*price)/100) + getResources().getString(R.string.ryal));

        tv_time.setText(Computrized_Model.getTime());
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        im_additionalservices = view.findViewById(R.id.im_additional_services);
        ll_next = view.findViewById(R.id.ll_next);
        tv_document = view.findViewById(R.id.tv_document);
        tv_cityf = view.findViewById(R.id.tv_cityf);
        tv_cityt = view.findViewById(R.id.tv_cityt);
        tv_num = view.findViewById(R.id.tv_num);
        tv_day = view.findViewById(R.id.tv_day);
        tv_price = view.findViewById(R.id.tv_price);
        tv_time = view.findViewById(R.id.tv_times);
        tv_total_pricesals=view.findViewById(R.id.tv_total_pricesals);

        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);

        } else {
            im_additionalservices.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startscdeule(1);
            }
        });

    }
    private void getappcommission() {
        // getbankaccounts();
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getappcommission("Bearer"+" "+ userModel.getToken()).enqueue(new Callback<Prectage_Model>() {
            @Override
            public void onResponse(Call<Prectage_Model> call, Response<Prectage_Model> response) {

                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    applydata(response.body());
                }
                else {
                    Log.e("ll",response.code()+""+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Prectage_Model> call, Throwable t) {
                try {
                    dialog.dismiss();
                    // smoothprogressbar.setVisibility(View.GONE);
                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage());
                } catch (Exception e) {
                }
            }
        });
    }

}
