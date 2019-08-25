package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Email_Address  extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
    private EditText edt_email;
    private Preferences preferences;
    private UserModel userModel;
private Button bt_save;
    public static Fragment_Email_Address newInstance() {
        return new Fragment_Email_Address();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_address, container, false);
initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        preferences= Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        edt_email=view.findViewById(R.id.edt_email);
        bt_save=view.findViewById(R.id.bt_save);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)


            {
                checkdata();
            }
        });
    }

    private void checkdata() {
        String email=edt_email.getText().toString();
              if(TextUtils.isEmpty(email)||! Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                  edt_email.setError("");
              }
              else {
                  updateemail(email);
              }
    }
    private void updateemail(String email) {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().updatermail(email,"Bearer" + " " + userModel.getToken()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    //preferences.create_update_userdata(activity,response.body());
                    //updateprofile(response.body());
                    activity.upadateprefrece(response.body());
                    activity.Back();
                } else {

                    Log.e("error_code", response.code() + "_" + response.errorBody() + response.message() + response.raw() + response.headers());


                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error", t.getMessage());
                Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
            }
        });
    }

}