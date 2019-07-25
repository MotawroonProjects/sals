package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Fragment_ٍEdit_Name extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
    private EditText edt_fname,edt_lname;
    private Button bt_save;
    private Preferences preferences;
    private UserModel userModel;
    public static Fragment_ٍEdit_Name newInstance() {
        return new Fragment_ٍEdit_Name();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container, false);
initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        edt_fname=view.findViewById(R.id.edt_fname);
        edt_lname=view.findViewById(R.id.edt_lname);
        bt_save=view.findViewById(R.id.bt_save);
        if(userModel!=null){
            if(userModel.getUser().getFirst_name()!=null){
                edt_fname.setText(userModel.getUser().getFirst_name());
            }
            if(userModel.getUser().getLast_name()!=null){
                edt_lname.setText(userModel.getUser().getLast_name());
            }
        }
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
            public void onClick(View view) {
                checkdata();
            }
        });
    }

    private void checkdata() {
        String fname=edt_fname.getText().toString();
        String lname=edt_lname.getText().toString();
        if(TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)){
            if(TextUtils.isEmpty(fname)){
                edt_fname.setError("");
            }
            if(TextUtils.isEmpty(lname)){
                edt_lname.setError("");
            }
        }
        else {
            Common.CloseKeyBoard(activity,edt_lname);
            updatename(fname,lname);
        }
    }

    private void updatename(String fname, String lname) {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().updateName(fname,lname,"Bearer" + " " + userModel.getToken()).enqueue(new Callback<UserModel>() {
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