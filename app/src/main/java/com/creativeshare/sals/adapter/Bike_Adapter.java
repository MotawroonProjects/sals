package com.creativeshare.sals.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.secdule.activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Bike_Adapter extends RecyclerView.Adapter<Bike_Adapter.Eyas_Holder> {
    List<Bike_Model.Sizes> list;
    Context context;
    private int row_index = 0;
    //  private String current_lang;
    private Scedule_Activity scedule_activity;
    // private int select;
    //private Fragment_Main fragment_main;

    public Bike_Adapter(List<Bike_Model.Sizes> list, Context context) {
        this.list = list;
        this.context = context;
        scedule_activity = (Scedule_Activity) context;
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
        Picasso.with(context).load(Uri.parse(Tags.IMAGE_URL + model.getImage())).fit().into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = i;
                notifyDataSetChanged();
            }
        });
        if (row_index == i) {
            viewHolder.tv_title.setTextColor(scedule_activity.getResources().getColor(R.color.colorPrimary));
            viewHolder.imageView.setColorFilter(scedule_activity.getResources().getColor(R.color.colorPrimary));
            viewHolder.fr_shape1.setBackgroundDrawable(scedule_activity.getResources().getDrawable(R.drawable.linear_shape_red));

            //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.tv_title.setTextColor(scedule_activity.getResources().getColor(R.color.black));
            viewHolder.imageView.setColorFilter(scedule_activity.getResources().getColor(R.color.gray4));
            viewHolder.fr_shape1.setBackgroundDrawable(scedule_activity.getResources().getDrawable(R.drawable.linear_shape));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_desc;
        ImageView imageView;
        FrameLayout fr_shape1;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_shape1);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            imageView = itemView.findViewById(R.id.im_shape1);
            fr_shape1 = itemView.findViewById(R.id.fr_shape1);

        }


    }
}