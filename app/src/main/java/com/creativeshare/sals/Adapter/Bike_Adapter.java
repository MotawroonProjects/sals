package com.creativeshare.sals.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Bike_Adapter extends RecyclerView.Adapter<Bike_Adapter.Eyas_Holder> {
    List<Bike_Model.Sizes> list;
    Context context;
  //  private String current_lang;
    //private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Bike_Adapter(List<Bike_Model.Sizes> list, Context context) {
        this.list = list;
        this.context = context;
      //  homeActivity = (Home_Activity) context;
       // Paper.init(homeActivity);
       // current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bike_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        Bike_Model.Sizes model = list.get(i);
      viewHolder.tv_title.setText(model.getTitle());
       viewHolder.tv_desc.setText(model.getDesc());
        Picasso.with(context).load(Uri.parse(Tags.IMAGE_URL+model.getImage())).fit().into(viewHolder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_desc;
ImageView imageView;
        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_shape1);
            tv_desc=itemView.findViewById(R.id.tv_desc);
imageView=itemView.findViewById(R.id.im_shape1);


        }


    }
}