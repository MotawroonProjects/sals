package com.creativeshare.sals.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Address_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.Eyas_Holder> {
    List<Address_Model.Addresses> list;
    Context context;
    private String current_lang;
    private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Address_Adapter(List<Address_Model.Addresses> list, Context context) {
        this.list = list;
        this.context = context;
        homeActivity = (Home_Activity) context;
        Paper.init(homeActivity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        String content = "";
        Address_Model.Addresses model = list.get(i);
        if (model.getAddress() != null) {
            viewHolder.tv_addrss.setText(model.getAddress());
        }
        if (model.getAddress_type() != null) {
            content += model.getAddress_type();
        }
        if (model.getBuilding_number() != null) {
            if (!content.isEmpty()) {
                content += ",";
            }
            content += model.getBuilding_number();
        }
        if (model.getFloor_number() != null) {
            if (!content.isEmpty()) {
                content += ",";
            }
            content += model.getFloor_number();
        }
        if (model.getFlat_number() != null) {
            if (!content.isEmpty()) {
                content += ",";
            }
            content += model.getFlat_number();
        }
        viewHolder.tv_content.setText(content);
        if (current_lang.equals("en")) {
            viewHolder.arrow.setRotation(180.0f);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_addrss, tv_content;
        ImageView arrow;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_addrss = itemView.findViewById(R.id.tv_address);

            tv_content = itemView.findViewById(R.id.tv_content);
            arrow = itemView.findViewById(R.id.arrow);
        }


    }
}