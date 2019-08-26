package com.creativeshare.sals.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Shipments_Recived;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Shipments_Sent;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Orders_Model;

import java.util.List;

public class Orders_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int ITEM_LOAD = 2;

    private List<Orders_Model.Orders.Data> data;
    private Context context;
    private Home_Activity activity;
    Fragment fragment;
    Fragment_Shipments_Sent fragment_shipments_sent;
    Fragment_Shipments_Recived fragment_shipments_recived;
    public Orders_Adapter(List<Orders_Model.Orders.Data> data, Context context, Fragment fragment) {

        this.data = data;
        this.context = context;
        activity=(Home_Activity)context;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_DATA) {
            View view = LayoutInflater.from(context).inflate(R.layout.shipments_row, parent, false);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_row, parent, false);
            return new LoadMoreHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {

            final MyHolder myHolder = (MyHolder) holder;
            final Orders_Model.Orders.Data data1 = data.get(myHolder.getAdapterPosition());
            if(data1.getIs_parcel()==0){
                ((MyHolder) holder).tv_title.setText(R.string.documents);

            }
            else {
                ((MyHolder) holder).tv_title.setText(R.string.parcels);

            }
            ((MyHolder) holder).tv_num.setText(context.getResources().getString(R.string.awbnumber)+" "+data1.getAwb_number());
            ((MyHolder) holder).tv_from.setText(data1.getFrom_city());
            ((MyHolder) holder).tv_to.setText(data1.getTo_city());
            ((MyHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fragment instanceof  Fragment_Shipments_Sent){
                    activity.setid(data.get((((MyHolder)holder).getLayoutPosition())),0);}
                    else  if(fragment instanceof  Fragment_Shipments_Recived){
                        activity.setid(data.get((((MyHolder)holder).getLayoutPosition())),1);}
                }
            });


            //Log.e("msg",advertsing.getMain_image());
        } else {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.progBar.setIndeterminate(true);
        }
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

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        private ProgressBar progBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            progBar = itemView.findViewById(R.id.progBar);
            progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemViewType(int position) {
    Orders_Model.Orders.Data data1 = data.get(position);
        if (data1 == null) {
            return ITEM_LOAD;
        } else {
            return ITEM_DATA;

        }


    }
}
