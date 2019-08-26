package com.creativeshare.sals.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Support_Catogry_Model;
import com.creativeshare.sals.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Issue_Cat_Adapter extends RecyclerView.Adapter<Issue_Cat_Adapter.Eyas_Holder> {
    List<Support_Catogry_Model.support_cats> list;
    Context context;
    private String current_lang;
    private Home_Activity homeActivity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Issue_Cat_Adapter(List<Support_Catogry_Model.support_cats> list, Context context) {
        this.list = list;
        this.context = context;
        homeActivity = (Home_Activity) context;
        Paper.init(homeActivity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.help_cat_row, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, final int i) {
        Support_Catogry_Model.support_cats model = list.get(i);
      viewHolder.tv_title.setText(model.getTitle());

        Picasso.with(context).load(Uri.parse(Tags.IMAGE_URL2+model.getImage())).fit().into(viewHolder.imageView);
viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        homeActivity.setcatid(list.get(viewHolder.getLayoutPosition()).getId());
    }
});


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_title;
ImageView imageView;
        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
imageView=itemView.findViewById(R.id.image);

        }


    }
}