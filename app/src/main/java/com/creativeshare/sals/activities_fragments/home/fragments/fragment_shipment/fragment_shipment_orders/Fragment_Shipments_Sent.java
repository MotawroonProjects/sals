package com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_shipment.fragment_shipment_orders;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Adapter.Orders_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Orders_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Shipments_Sent extends Fragment {
    private Home_Activity activity;
    private String current_lang;
    private Preferences preferences;
    private UserModel userModel;
private RecyclerView rec_sent;
private List<Orders_Model.Orders.Data> dataList;
private Orders_Adapter orders_adapter;
    private GridLayoutManager manager;
    private boolean isLoading = false;
    private int current_page = 1;
    private ProgressBar progBar;
    private LinearLayout ll_no_order;
    public static Fragment_Shipments_Sent newInstance() {
        return new Fragment_Shipments_Sent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipments_sent, container, false);
        initView(view);
        getorders();
        return view;
    }
    private void initView(View view) {
        dataList=new ArrayList<>();
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        rec_sent=view.findViewById(R.id.rec_sent);
        progBar = view.findViewById(R.id.progBar);
        ll_no_order = view.findViewById(R.id.ll_no_order);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        rec_sent.setDrawingCacheEnabled(true);
        rec_sent.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rec_sent.setItemViewCacheSize(25);
        manager=new GridLayoutManager(activity,1);
        rec_sent.setLayoutManager(manager);
        orders_adapter=new Orders_Adapter(dataList,activity,this);
        rec_sent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int total_item = orders_adapter.getItemCount();
                    int last_item_pos = manager.findLastCompletelyVisibleItemPosition();
                    //  Log.e("msg", total_item + "  " + last_item_pos);
                    if (last_item_pos >= (total_item - 5) && !isLoading ) {
                        isLoading = true;
                        dataList.add(null);
                        orders_adapter.notifyItemInserted(dataList.size() - 1);
                        int page = current_page + 1;

                            loadMore(page);

                    }
                }
            }
        });
        rec_sent.setAdapter(orders_adapter);


    }
    public void getorders() {
     //   Common.CloseKeyBoard(homeActivity, edt_name);

       // rec_sent.setVisibility(View.GONE);
        progBar.setVisibility(View.VISIBLE);
        ll_no_order.setVisibility(View.GONE);

        Api.getService()
                .getsentorders(1,"Bearer "+" "+userModel.getToken(),current_lang)
                .enqueue(new Callback<Orders_Model>() {
                    @Override
                    public void onResponse(Call<Orders_Model> call, Response<Orders_Model> response) {
                        progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null && response.body().getOrders().getData() != null) {
                            dataList.clear();
                            dataList.addAll(response.body().getOrders().getData());
                            if (response.body().getOrders().getData().size() > 0) {
                               // rec_sent.setVisibility(View.VISIBLE);
Log.e("lll",response.body().getOrders().getData().get(0).getAwb_number());
                                ll_no_order.setVisibility(View.GONE);
                                orders_adapter.notifyDataSetChanged();
                             //   total_page = response.body().getMeta().getLast_page();

                            } else {
                                ll_no_order.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            try {
                                Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders_Model> call, Throwable t) {
                        try {

                            progBar.setVisibility(View.GONE);
                            ll_no_order.setVisibility(View.VISIBLE);
                           // Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("error", t.getMessage());
                        } catch (Exception e) {
                        }
                    }
                });

    }
    private void loadMore(int page) {
        Api.getService()
                .getsentorders(page,"Bearer "+" "+userModel.getToken(),current_lang)
                .enqueue(new Callback<Orders_Model>() {
                    @Override
                    public void onResponse(Call<Orders_Model> call, Response<Orders_Model> response) {
                        dataList.remove(dataList.size() - 1);
                        orders_adapter.notifyItemRemoved(dataList.size() - 1);
                        isLoading = false;
                        if (response.isSuccessful() && response.body() != null && response.body().getOrders().getData() != null) {

                            dataList.addAll(response.body().getOrders().getData());
                            // categories.addAll(response.body().getCategories());
                            current_page = response.body().getOrders().getCurrent_page();
                            orders_adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            try {
                                Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders_Model> call, Throwable t) {
                        try {
                            dataList.remove(dataList.size() - 1);
                            orders_adapter.notifyItemRemoved(dataList.size() - 1);
                            isLoading = false;
                            //Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("error", t.getMessage());
                        } catch (Exception e) {
                        }
                    }
                });
    }



}
