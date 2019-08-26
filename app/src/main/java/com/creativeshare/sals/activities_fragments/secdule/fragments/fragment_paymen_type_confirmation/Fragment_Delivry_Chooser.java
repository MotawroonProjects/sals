package com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Prectage_Model;
import com.creativeshare.sals.models.Shipment_Send_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Delivry_Chooser extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;

    private Button next;
    private LinearLayout ll_additional_services,ll_sadad,ll_credit;
    private ImageView im_additional_Services,arrow1,im_sadad,im_credit;
    private TextView tv_document,  tv_cityt, tv_day, tv_num, tv_total_pricedhl,tv_total_pricesals,tv_address;
    private Preferences preferences;
    private UserModel userModel;
    private Double price;
    public static Fragment_Delivry_Chooser newInstance() {
        return new Fragment_Delivry_Chooser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivry_choose, container, false);
        initView(view);
        getappcommission();
        return view;
    }

    private void initView(View view) {
        Shipment_Send_Model.setcredit("creditcard");
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_additional_services = view.findViewById(R.id.ll_additional_Services);
         arrow1=view.findViewById(R.id.arrow1);
        im_additional_Services = view.findViewById(R.id.im_additional_services);
        tv_address=view.findViewById(R.id.tv_address);
        tv_document = view.findViewById(R.id.tv_document);
     //   tv_cityf = view.findViewById(R.id.tv_cityf);
        tv_cityt = view.findViewById(R.id.tv_cityt);
        tv_num = view.findViewById(R.id.tv_num);
        tv_day = view.findViewById(R.id.tv_day);
        tv_total_pricedhl = view.findViewById(R.id.tv_total_pricedhl);
        tv_total_pricesals=view.findViewById(R.id.tv_total_pricesals);
     //   tv_time = view.findViewById(R.id.tv_times);
        ll_sadad=view.findViewById(R.id.ll_sadad);
        ll_credit=view.findViewById(R.id.ll_credit);
        im_sadad=view.findViewById(R.id.im_sadad);
        im_credit=view.findViewById(R.id.im_credit);
        next = view.findViewById(R.id.bt_next);
ll_sadad.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Shipment_Send_Model.setcredit("SADAD");
        im_credit.setVisibility(View.GONE);
        im_sadad.setVisibility(View.VISIBLE);
    }
});
ll_credit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Shipment_Send_Model.setcredit("creditcard");
        im_credit.setVisibility(View.VISIBLE);
        im_sadad.setVisibility(View.GONE);
    }
});
        if (current_lang.equals("en")) {
            im_additional_Services.setRotation(180.0f);
            arrow1.setRotation(180.0f);
        }
        ll_additional_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentAdditionalservices();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                activity.DisplayFragmentconfirmation();
            }
        });

    }
    private void applydata() {
        if(Shipment_Send_Model.getParcel().equals("0")){
            tv_document.setText(getResources().getString(R.string.documents));

        }
        else {
            tv_document.setText(getResources().getString(R.string.parcels));

        }
       // tv_cityf.setText(Computrized_Model.getCity_From());
        tv_address.setText(Shipment_Send_Model.getAdddresst());
        tv_cityt.setText(Shipment_Send_Model.getCityt());
        tv_num.setText(Shipment_Send_Model.getWegights().size()+" " + getResources().getString(R.string.pieces) +Shipment_Send_Model.getWegights().get(0) + getResources().getString(R.string.kg));
        tv_day.setText(getResources().getString(R.string.Delivery)+" "+Shipment_Send_Model.getDay_number()+getResources().getString(R.string.days));
        tv_total_pricedhl.setText(getResources().getString(R.string.from_dhl)+" "+Shipment_Send_Model.getPrice() + getResources().getString(R.string.ryal));
        tv_total_pricesals.setText(getResources().getString(R.string.from_sals)+" "+(Double.parseDouble(Shipment_Send_Model.getPrice())-(Double.parseDouble(Shipment_Send_Model.getPrice())*price)/100) + getResources().getString(R.string.ryal));
Shipment_Send_Model.setPrice((Double.parseDouble(Shipment_Send_Model.getPrice())-(Double.parseDouble(Shipment_Send_Model.getPrice())*price)/100)+"");

        // tv_day.setText(Computrized_Model.getTime());
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
                    updateappcommission(response.body());
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

    private void updateappcommission(Prectage_Model body) {
        price=Double.parseDouble(body.getRate());
        applydata();

    }
}
