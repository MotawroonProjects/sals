package com.creativeshare.sals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Track_Model;

import java.util.List;

public class Track_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Track_Model.AWBInfo> data;
    private Context context;
    //   private Home_Activity activity;

    public Track_Adapter(List<Track_Model.AWBInfo> data, Context context) {

        this.data = data;
        this.context = context;
//        activity=(Home_Activity)context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.shipments_row, parent, false);
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {


        final MyHolder myHolder = (MyHolder) holder;
        final Track_Model.AWBInfo data1 = data.get(position);
        ((MyHolder) holder).tv_title.setText(data1.getShipmentInfo().getShipperName());

        ((MyHolder) holder).tv_num.setText(context.getResources().getString(R.string.awbnumber) + " " + data1.getAWBNumber());
        ((MyHolder) holder).tv_from.setText(data1.getShipmentInfo().getOriginServiceArea().getDescription());
        ((MyHolder) holder).tv_to.setText(data1.getShipmentInfo().getDestinationServiceArea().getDescription());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_num, tv_from, tv_to;

        public MyHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);

            tv_num = itemView.findViewById(R.id.tv_num);
            tv_from = itemView.findViewById(R.id.tv_from);
            tv_to = itemView.findViewById(R.id.tv_to);


        }

    }


}
