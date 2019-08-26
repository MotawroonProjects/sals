package com.creativeshare.sals.Activities_Fragments.Secdule.Fragments.fragment_shipping_detials;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Dementions_Model;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Shipping_Dementions extends Fragment {
    private Scedule_Activity activity;
    private String current_lang;
    private ImageView back_arrow;
private EditText edt_length,edt_width,edt_hight,edt_grossweight,edt_totalweight;
private TextView tv_volumeweight;
private Button bt_save;
private double length,width,hight,voulumeweight,grossweight,totalweigt;
private Dementions_Model dementions_model;
    public static Fragment_Shipping_Dementions newInstance() {
        return new Fragment_Shipping_Dementions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping_dimintions, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Scedule_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

edt_length=view.findViewById(R.id.edt_length);
edt_width=view.findViewById(R.id.edt_width);
edt_hight=view.findViewById(R.id.edt_hight);
edt_grossweight=view.findViewById(R.id.edt_grossweight);
edt_totalweight=view.findViewById(R.id.edt_totalweight);
tv_volumeweight=view.findViewById(R.id.tv_volumeweight);
bt_save=view.findViewById(R.id.bt_save);
        back_arrow = view.findViewById(R.id.arrow);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });

edt_width.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            width=Double.parseDouble(edt_width.getText().toString());

        }catch (Exception e){
            width=0;
        }
    }
});
        edt_length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    length=Double.parseDouble(edt_length.getText().toString());

                }catch (Exception e){
                    length=0;
                }
            }
        });
edt_hight.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(edt_width.getText().toString().isEmpty()||edt_length.getText().toString().isEmpty()){
            if(edt_width.getText().toString().isEmpty()){
                edt_width.setError(getResources().getString(R.string.field_req));
            }
            if(edt_length.getText().toString().isEmpty()){
                edt_length.setError(getResources().getString(R.string.field_req));
            }


        }
        else {
            width=Double.parseDouble(edt_width.getText().toString());
            length=Double.parseDouble(edt_length.getText().toString());
            if(!edt_hight.getText().toString().isEmpty()){
                hight=Double.parseDouble(edt_hight.getText().toString());
                voulumeweight=(hight*length*width)/5000;
                tv_volumeweight.setText(((hight*width*length)/5000)+"");
            }
            else {
                hight=0;
               // hight=Double.parseDouble(edt_hight.getText().toString());
                voulumeweight=(hight*length*width)/5000;
                tv_volumeweight.setText(((hight*width*length)/5000)+"");
            }
        }


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
       // String grw=edt_grossweight.getText().toString();
        //String totw=edt_totalweight.getText().toString();
        dementions_model=new Dementions_Model();
        if(length!=0&&width!=0&&hight!=0){
            if(voulumeweight==0){
                voulumeweight=(hight*width*length)/5000;
            }
           // grossweight=Double.parseDouble(grw);
            //totalweigt=Double.parseDouble(totw);
            dementions_model.setWidth((int)width);
            dementions_model.setHight((int)hight);
            dementions_model.setLength((int)length);
            dementions_model.setVoulumeweight(voulumeweight);
            //dementions_model.setGrossweight(grossweight);
            //dementions_model.setTotalweigt(totalweigt);
            activity.sentdemminssion(dementions_model);
            activity.Back();
        }
        else {
            if(length==0){
                edt_length.setError(getResources().getString(R.string.field_req));
            }
            if(width==0){
                edt_width.setError(getResources().getString(R.string.field_req));
            }

            if(hight==0){
                edt_hight.setError(getResources().getString(R.string.field_req));
            }/*
            if(TextUtils.isEmpty(grw)){
                edt_grossweight.setError(getResources().getString(R.string.field_req));
            }
            if(TextUtils.isEmpty(totw)){
                edt_totalweight.setError(getResources().getString(R.string.field_req));
            }*/
        }
    }


}
