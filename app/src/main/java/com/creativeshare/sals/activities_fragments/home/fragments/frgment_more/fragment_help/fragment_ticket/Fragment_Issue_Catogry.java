package com.creativeshare.sals.activities_fragments.home.fragments.frgment_more.fragment_help.fragment_ticket;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.adapter.Issue_Cat_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Support_Catogry_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Issue_Catogry extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;

    private RecyclerView rec_help_cat;
    private List<Support_Catogry_Model.support_cats> categories;
    private Issue_Cat_Adapter issue_cat_adapter;
private Preferences preferences;
private UserModel userModel;
    public static Fragment_Issue_Catogry newInstance() {
        return new Fragment_Issue_Catogry();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_issue_catogry, container, false);
initView(view);
getHelpcat();
        return view;
    }

    private void getHelpcat() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getIssue("Bearer" + " " + userModel.getToken(),current_lang).enqueue(new Callback<Support_Catogry_Model>() {
            @Override
            public void onResponse(Call<Support_Catogry_Model> call, Response<Support_Catogry_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getSupport_cats().size()>0) {
                    categories.clear();
                    categories.addAll(response.body().getSupport_cats());
                    issue_cat_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Support_Catogry_Model> call, Throwable t) {
                //progBar.setVisibility(View.GONE);
                dialog.dismiss();
                try {
                    Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
                    Log.e("Error ", t.getMessage());

                } catch (Exception e) {

                }
            }
        });

    }

    private void initView(View view) {
        categories=new ArrayList<>();
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);

        rec_help_cat=view.findViewById(R.id.rec_help);
        issue_cat_adapter =new Issue_Cat_Adapter(categories,activity);
        rec_help_cat.setLayoutManager(new GridLayoutManager(activity,1));
        rec_help_cat.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rec_help_cat.setDrawingCacheEnabled(true);
        rec_help_cat.setItemViewCacheSize(25);
        rec_help_cat.setAdapter(issue_cat_adapter);
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