package com.creativeshare.sals.activities_fragments.home.fragments.frgment_more.fragment_help;

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

import com.creativeshare.sals.R;
import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.adapter.Help_Cat_Adapter;
import com.creativeshare.sals.adapter.Help_Cat_question_Adapter;
import com.creativeshare.sals.adapter.Question_Adapter;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.models.Move_Data_Model;
import com.creativeshare.sals.models.Questions_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.creativeshare.sals.share.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Help_Detials extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;

    private RecyclerView rec_help_cat;
    private List<Help_Cat_Model.Categories.Faqs> categories;
    private Help_Cat_question_Adapter help_cat_adapter;
private Preferences preferences;
private UserModel userModel;
    public static Fragment_Help_Detials newInstance() {
        return new Fragment_Help_Detials();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_detials, container, false);
initView(view);
        return view;
    }



    private void initView(View view) {
        categories=new ArrayList<>();
        categories= Move_Data_Model.getFaqs();
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        rec_help_cat=view.findViewById(R.id.rec_help);
        help_cat_adapter=new Help_Cat_question_Adapter(categories,activity);
        rec_help_cat.setLayoutManager(new GridLayoutManager(activity,1));
        rec_help_cat.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rec_help_cat.setDrawingCacheEnabled(true);
        rec_help_cat.setItemViewCacheSize(25);
        rec_help_cat.setAdapter(help_cat_adapter);
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