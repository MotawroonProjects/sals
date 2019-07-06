package com.creativeshare.sals.Activities_Fragments.Registration.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Confirm_Code;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Service_Centers;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Signup;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Track_The_Shipment;
import com.creativeshare.sals.Language.Language;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Register_Activity   extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment_Signup fragment_signup;
    private Fragment_Confirm_Code fragment_confirm_code;
    private Fragment_Service_Centers fragment_service_centers;
    private Fragment_Track_The_Shipment fragment_track_the_shipment;
    private int fragment_count=0;
    private String current_lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);
      Paper.init(this);
      current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
      fragmentManager = this.getSupportFragmentManager();

        if (savedInstanceState == null) {
            DisplayFragmentsignup();
        }

    }




    public void DisplayFragmentsignup() {
      fragment_count+=1;


            fragment_signup = Fragment_Signup.newInstance();


        if (fragment_signup.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_signup).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container ,fragment_signup, "fragment_signup").addToBackStack("fragment_signup").commit();
        }

    }
    public void DisplayFragmentconfirmcode() {
        fragment_count+=1;


        fragment_confirm_code = Fragment_Confirm_Code.newInstance();


        if (fragment_confirm_code.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_confirm_code).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_confirm_code, "fragment_confirm_code").addToBackStack("fragment_confirm_code").commit();
        }
    }
    public void DisplayFragmentServiceCenters() {
        fragment_count+=1;


        fragment_service_centers = Fragment_Service_Centers.newInstance(1);


        if (fragment_service_centers.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_service_centers).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_service_centers, "fragment_service_centers").addToBackStack("fragment_service_centers").commit();
        }
    }
    public void DisplayFragmentTrackTheShipment() {
        fragment_count+=1;


        fragment_track_the_shipment = Fragment_Track_The_Shipment.newInstance(1);


        if (fragment_track_the_shipment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_track_the_shipment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_track_the_shipment, "fragment_track_the_shipment").addToBackStack("fragment_track_the_shipment").commit();
        }
    }
    @Override
    public void onBackPressed() {
        Back();
    }

 public void Back() {
      if(fragment_count>1){
          fragment_count-=1;
          super.onBackPressed();
      }
      else {
          finish();
      }
    }
    public void openhomeactivity(){
        Intent intent=new Intent(Register_Activity.this, Home_Activity.class);
        startActivity(intent);
        finish();
    }
}
