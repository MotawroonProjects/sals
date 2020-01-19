package com.creativeshare.sals.activities_fragments.registration.fragments.fragment_signup_confirm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.activities_fragments.registration.activity.Register_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Confirm_Code extends Fragment {
    private Register_Activity register_activity;
    private Preferences preferences;
    private String current_lang;
    private ImageView back_arrow,im1;
    private Button bt_check;
    private TextView tv_resend;
    private EditText edt_confirm_code;
    private boolean reseend;
    private static String TAG1 = "phone_code";
    private static String TAG2 = "phone_number";
    private String phone, phone_code;
    private CountDownTimer countDownTimer;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String id;
    private String code;
    private FirebaseAuth mAuth;
    private ProgressDialog dialo;

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
        im1=view.findViewById(R.id.im1);
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
                    im1.setImageResource(R.drawable.ic_empty);

                    couter();
                    sendverficationcode(phone,phone_code.replace("00","+"));
                }
            }
        });
authn();
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        sendverficationcode(phone,phone_code.replace("00","+"));
    }
},3);
        couter();

    }

    private void authn() {

        mAuth= FirebaseAuth.getInstance();

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("id",s);
                id=s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Log.e("code",phoneAuthCredential.getSmsCode());
//phoneAuthCredential.getProvider();
                if(phoneAuthCredential.getSmsCode()!=null){
                code=phoneAuthCredential.getSmsCode();
                edt_confirm_code.setText(code);
              siginwithcredental(phoneAuthCredential);}
                else {
                    siginwithcredental(phoneAuthCredential);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("llll",e.getMessage());
            }


        };

    }

    private void resendcode() {
        final ProgressDialog dialog = Common.createProgressDialog(register_activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService().resendsms(phone_code, phone).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    im1.setImageResource(R.drawable.ic_empty);
                    couter();

                } else {
                    try {
                        Log.e("error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(register_activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(register_activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void checkdata() {
       code = edt_confirm_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            edt_confirm_code.setError(getResources().getString(R.string.field_req));
        } else {
           // checkconfirmation(code);
            verfiycode(code);
        }
    }

    private void checkconfirmation( String code) {

code=code.replaceFirst(code.charAt(0)+"","");
code=code.replaceFirst(code.charAt(0)+"","");
Log.e("code",code);
Api.getService().checkcode(phone_code, phone, code).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialo.dismiss();
                if (response.isSuccessful()) {
                    //Log.e("ssssssssssss",response.code()+response.body().getMessage());
                    countDownTimer.cancel();
                    preferences.create_update_userdata(register_activity, response.body());
                    register_activity.openhomeactivity();

                } else {
                    Log.e("msg", response.code() + "" + response.errorBody() + "" + response.raw() + response.message() + phone + phone_code + "  "  + response.headers() + response.raw());
                    if (response.code() == 404) {
                        Common.CreateSignAlertDialog(register_activity, getString(R.string.inc_code_verification));
                    } else {

                        Toast.makeText(register_activity, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialo.dismiss();
                Toast.makeText(register_activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void couter() {
        countDownTimer = new CountDownTimer(59000, 1000) {
            @Override
            public void onTick(long l) {


                reseend = false;
                tv_resend.setText("00 :" + l / 1000);
            }

            @Override
            public void onFinish() {
                reseend = true;
                tv_resend.setText(register_activity.getResources().getString(R.string.resend));
                im1.setImageResource(R.drawable.ic_checked);
            }
        }.start();
    }
    private void verfiycode(String code) {
       // Toast.makeText(register_activity,code,Toast.LENGTH_LONG).show();

        Log.e("ccc",code);
        if(id!=null){

            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(id,code);
        siginwithcredental(credential);}
    }

    private void siginwithcredental(PhoneAuthCredential credential) {
        dialo = Common.createProgressDialog(register_activity,getString(R.string.wait));
        dialo.setCancelable(false);
        dialo.show();
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
Log.e("data",phone);
                    // activity.NavigateToHomeActivity();
                    mAuth.signOut();
                    if(code!=null){
checkconfirmation(code);           }
                    else{
                        checkconfirmation("123456");
                    }
                }


            }
        });
    }

    private void sendverficationcode(String phone, String phone_code) {
        Log.e("kkk",phone_code+phone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_code+phone,59, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,  mCallbacks);

    }
}
