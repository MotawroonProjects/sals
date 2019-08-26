package com.creativeshare.sals.activities_fragments.home.fragments.fragment_profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.activities_fragments.home.activity.Home_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.share.Common;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;

import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Profile extends Fragment {
    private Home_Activity activity;
    private ImageView arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, back_arrow,im1,im2,im3,im4;
    private TextView tv_fname, tv_lname,tv_phone,tv_email;
    private ConstraintLayout co_name, co_address, co_phone, co_email, co_language;
    private LinearLayout ll_mange_card;
    private FrameLayout fr_logout;
    private SwitchCompat switch_notify;
    private String current_lang;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Profile newInstance() {
        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);

        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        arrow1 = view.findViewById(R.id.arrow1);
        arrow2 = view.findViewById(R.id.arrow2);
        arrow3 = view.findViewById(R.id.arrow3);
        arrow4 = view.findViewById(R.id.arrow4);
        arrow5 = view.findViewById(R.id.arrow5);
        arrow6 = view.findViewById(R.id.arrow6);
        im1=view.findViewById(R.id.im1);
        im2=view.findViewById(R.id.im2);
        im3=view.findViewById(R.id.im3);
        im4=view.findViewById(R.id.im4);

        tv_fname = view.findViewById(R.id.tv_fname);
        tv_lname = view.findViewById(R.id.tv_lname);
        tv_phone=view.findViewById(R.id.tv_phone);
        tv_email=view.findViewById(R.id.tv_email);
        co_address = view.findViewById(R.id.co_address);
        co_email = view.findViewById(R.id.co_email);
        co_name = view.findViewById(R.id.co_name);
        co_phone = view.findViewById(R.id.co_phone);
        co_language = view.findViewById(R.id.co_language);
        ll_mange_card = view.findViewById(R.id.ll_mangecard);
        fr_logout = view.findViewById(R.id.fr_logout);
        switch_notify = view.findViewById(R.id.switch_notify);


        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        } else if (current_lang.equals("en")) {
            arrow1.setRotation(180.0f);
            arrow2.setRotation(180.0f);
            arrow3.setRotation(180.0f);
            arrow4.setRotation(180.0f);
            arrow5.setRotation(180.0f);
            arrow6.setRotation(180.0f);
        }
        switch_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null) {
                    changenotifystatus();
                }
            }

        });
        co_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentMyaddress();
            }
        });
        co_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEditphone();
            }
        });
        co_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEmailAddress();
            }
        });
        ll_mange_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentAddcreditCard();
            }
        });
        co_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentEditname();

            }
        });
        co_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DisplayFragmentLanguage();
            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        fr_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        updateprofile(userModel);
    }

    private void updateprofile(UserModel userModel) {
        if (userModel == null) {
            switch_notify.setChecked(false);
        } else {

            if (userModel.getUser().getIs_notifiable() == 1) {
                // Log.e("stat",userModel.getUser().getIs_notifiable()+"");
                switch_notify.setChecked(true);
            } else {
                switch_notify.setChecked(false);
            }
            if(userModel.getUser().getFirst_name()!=null){
                tv_fname.setText(userModel.getUser().getFirst_name());
                im1.setImageResource(R.drawable.ic_checked);
            }

            else {
                im1.setImageResource(R.drawable.ic_empty);

            }
            if(userModel.getUser().getLast_name()!=null){
                tv_lname.setText(userModel.getUser().getLast_name());
            }

            if(userModel.getUser().getMobile_number()!=null){
                tv_phone.setText(userModel.getUser().getMobile_number());
                im3.setImageResource(R.drawable.ic_checked);

            }
            else {
                im3.setImageResource(R.drawable.ic_empty);

            }
            if(userModel.getUser().getEmail()!=null){
                tv_email.setText(userModel.getUser().getEmail());
                im4.setImageResource(R.drawable.ic_checked);

            }
            else {
                im4.setImageResource(R.drawable.ic_empty);

            }
            if(userModel.getAddresses()!=null){
                im2.setImageResource(R.drawable.ic_checked);

            }
            else {
                im2.setImageResource(R.drawable.ic_empty);

            }
        }
    }

    private void changenotifystatus() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Log.e("user", userModel.getToken());
        Api.getService().changenotifystatus("Bearer" + " " + userModel.getToken()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    //preferences.create_update_userdata(activity,response.body());
                    //updateprofile(response.body());
                    upadateprefrece(response.body());
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

    public void upadateprefrece(UserModel userModel) {
        userModel.setToken(this.userModel.getToken());
        preferences.create_update_userdata(activity, userModel);
        updateprofile(userModel);
    }

    private void Logout() {
        final ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Log.e("token", "Bearer" + userModel.getToken());
        Api.getService().Logout("Bearer" + " " + userModel.getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    activity.Logout();
                } else {

                    Log.e("error_code", response.code() + "_" + response.errorBody() + response.message() + response.raw() + response.headers());


                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error", t.getMessage());
                Toast.makeText(activity, getResources().getString(R.string.something), Toast.LENGTH_LONG).show();
            }
        });
    }

}
