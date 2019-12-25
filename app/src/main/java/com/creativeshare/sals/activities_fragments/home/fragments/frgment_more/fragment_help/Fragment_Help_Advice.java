package com.creativeshare.sals.activities_fragments.home.fragments.frgment_more.fragment_help;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.adapter.Help_Cat_Adapter;
import com.creativeshare.sals.adapter.Question_Adapter;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.models.Questions_Model;
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

public class Fragment_Help_Advice extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
private RecyclerView rec_question;
private List<Questions_Model.Faqs> faqs;
private Question_Adapter question_adapter;
    private RecyclerView rec_help_cat;
    private List<Help_Cat_Model.Categories> categories;
    private Help_Cat_Adapter help_cat_adapter;
private Preferences preferences;
private UserModel userModel;
    private TextView tv_phone,tv_link;
    Intent intent ;
    private static final int REQUEST_PHONE_CALL = 1;

    public static Fragment_Help_Advice newInstance() {
        return new Fragment_Help_Advice();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_help_advice, container, false);
initView(view);
getquestion();
        return view;
    }

    private void getquestion() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getQuestions("Bearer" + " " + userModel.getToken(),current_lang).enqueue(new Callback<Questions_Model>() {
            @Override
            public void onResponse(Call<Questions_Model> call, Response<Questions_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getFaqs().size()>0) {
                    faqs.clear();
                   faqs.addAll(response.body().getFaqs());
                    question_adapter.notifyDataSetChanged();
                    getHelpcat();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Questions_Model> call, Throwable t) {
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
    private void getHelpcat() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().getHelpcat("Bearer" + " " + userModel.getToken(),current_lang).enqueue(new Callback<Help_Cat_Model>() {
            @Override
            public void onResponse(Call<Help_Cat_Model> call, Response<Help_Cat_Model> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getCategories().size()>0) {
                    categories.clear();
                    categories.addAll(response.body().getCategories());
                    help_cat_adapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.e("Error Code", response.code() + "_" + response.errorBody());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Help_Cat_Model> call, Throwable t) {
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
        faqs=new ArrayList<>();
        categories=new ArrayList<>();
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+9200 03450",null));

        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        rec_question=view.findViewById(R.id.rec_question);
        question_adapter=new Question_Adapter(faqs,activity);
        rec_question.setLayoutManager(new GridLayoutManager(activity,1));
        rec_question.setAdapter(question_adapter);
        rec_help_cat=view.findViewById(R.id.rec_help_cat);
        help_cat_adapter=new Help_Cat_Adapter(categories,activity);
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
        tv_link=view.findViewById(R.id.tv_link);
        tv_phone=view.findViewById(R.id.tv_phone_help);

        tv_link.setPaintFlags(tv_link.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startcustomactivity("https://www.logistics.dhl/global-en/home/contact-us.html");
            }
        });
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent!=null){
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        startActivity(intent);
                    }
                }}
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                }
                else {

                }
                return;
            }
        }
    }
}