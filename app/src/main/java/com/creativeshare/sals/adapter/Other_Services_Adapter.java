package com.creativeshare.sals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Other_Services_Model;

import java.util.List;

public class Other_Services_Adapter extends RecyclerView.Adapter<Other_Services_Adapter.Eyas_Holder> {
    List<Other_Services_Model.Services> list;
    Context context;
    // private String current_lang;
    // private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Other_Services_Adapter(List<Other_Services_Model.Services> list, Context context) {
        this.list = list;
        this.context = context;
        //  homeActivity = (Home_Activity) context;
        //Paper.init(homeActivity);
        //current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_service_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        Other_Services_Model.Services model = list.get(i);
        viewHolder.com_title.setText(model.getTitle());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        CheckBox com_title;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            com_title = itemView.findViewById(R.id.com_title);


        }


    }
}