package com.creativeshare.sals.Activities_Fragments.Registration.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
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

public class Fragment_Confirm_Code extends Fragment {
    private Register_Activity register_activity;
    private Preferences preferences;
    private String current_lang;
    private ImageView back_arrow;
    private Button bt_check;
    private TextView tv_resend;
    private EditText edt_confirm_code;
    private boolean reseend;
    private static String TAG1 = "phone_code";
    private static String TAG2 = "phone_number";
    private String phone, phone_code;
    private CountDownTimer countDownTimer;

    public static Fragment_Confirm_Code newInstance(String phone, String phone_code) {
        Fragment_Confirm_Code fragment_confirm_code = new Fragment_Confirm_Code();
        Bundle bundle = new Bundle();
        bundle.putString(TAG1, phone_code);
        bundle.putString(TAG2, phone);
        fragment_confirm_code.setArguments(bundle);
        return fragment_confirm_code;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmcode, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        phone = getArguments().getString(TAG2);
        phone_code = getArguments().getString(TAG1);
        register_activity = (Register_Activity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(register_activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        edt_confirm_code = view.findViewById(R.id.edt_confirm_code);
        bt_check = view.findViewById(R.id.bt_check);
        tv_resend = view.findViewById(R.id.tv_resend);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_activity.Back();
            }
        });
        bt_check.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            checkdata();
                                        }
                                    }
        );
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reseend) {

                }
            }
        });
        couter();

    }

    private void checkdata() {
        String code = edt_confirm_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            edt_confirm_code.setError(getResources().getString(R.string.field_req));
        } else {
            checkconfirmation(code);
        }
    }

    private void checkconfirmation(String code) {
        final ProgressDialog progressDialog = Common.createProgressDialog(register_activity, getResources().getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Api.getService().checkcode(phone_code, phone, code).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    //Log.e("ssssssssssss",response.code()+response.body().getMessage());
                    countDownTimer.cancel();
                    preferences.create_update_userdata(register_activity, response.body());
                    register_activity.openhomeactivity();

                } else {
                    Log.e("msg",response.code()+""+response.errorBody()+""+response.raw()+response.message()+phone+phone_code+"  "+code+response.headers()+response.raw());
                    if (response.code() == 404) {
                        Common.CreateSignAlertDialog(register_activity, getString(R.string.inc_code_verification));
                    } else {

                        Toast.makeText(register_activity, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(register_activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void couter() {
        countDownTimer = new CountDownTimer(47000, 1000) {
            @Override
            public void onTick(long l) {
                reseend = false;
                tv_resend.setText("00 :" + l / 1000);
            }

            @Override
            public void onFinish() {
                reseend = true;
                tv_resend.setText(getResources().getString(R.string.resend));
            }
        }.start();
    }
}
