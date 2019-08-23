package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Orders_Model;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_ٍShipment_Details extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
    private Orders_Model.Orders.Data data;
    final static private String Tag = "Shipp";
private TextView tv_book,tv_num,tv_trucknum,tv_to,tv_from,tv_price;
    public static Fragment_ٍShipment_Details newInstance(Orders_Model.Orders.Data id) {
Fragment_ٍShipment_Details fragment_ٍShipment_details=new Fragment_ٍShipment_Details();
    Bundle bundle=new Bundle();
    bundle.putSerializable(Tag,id);
    fragment_ٍShipment_details.setArguments(bundle);
    return fragment_ٍShipment_details;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipment_detials, container, false);
initView(view);
showdata();
        return view;
    }

    private void showdata() {
        if(data!=null){
            if(data.getIs_parcel()==0){
                tv_book.setText(getResources().getString(R.string.documents));
            }
            else {
                tv_book.setText(getResources().getString(R.string.parcels));

            }
            tv_num.setText(data.getTotal_items());
            tv_from.setText(getResources().getString(R.string.from)+" "+data.getFrom_city());
            tv_to.setText(getResources().getString(R.string.to)+" "+data.getTo_city());
tv_trucknum.setText(data.getAwb_number());
//tv_price.setText(data.get);
        }
    }

    private void initView(View view)
    {
        data= (Orders_Model.Orders.Data) getArguments().getSerializable(Tag);
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        tv_book=view.findViewById(R.id.tv_book);
        tv_num=view.findViewById(R.id.tv_num_truck);
        tv_to=view.findViewById(R.id.tv_to);
        tv_from=view.findViewById(R.id.tv_from);
        tv_price=view.findViewById(R.id.tv_price);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
    }
}