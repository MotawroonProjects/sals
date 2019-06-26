package com.creativeshare.sals.Activities_Fragments.Home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Calculate_price;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Computrized_Price;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Email_Address;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Home;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Language;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Payments;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Profile;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Main;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_ٍShipments;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.Fragment_Track_The_Shipment;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Language.Language;
import com.creativeshare.sals.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Home_Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_Calculate_price fragment_calculate_price;
    private Fragment_Computrized_Price fragment_computrized_price;
    private Fragment_Profile fragment_profile;
    private Fragment_Payments fragment_payments;
    private Fragment_ٍShipments fragment_ٍShipments;
    private Fragment_Language fragment_language;
    private Fragment_Email_Address fragment_email_address;
    private Fragment_Track_The_Shipment fragment_track_the_shipment;
    private int fragment_count = 0;
    private String current_lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = this.getSupportFragmentManager();
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            DisplayFragmentHome();
            DisplayFragmentMain();
        }

    }


    public void DisplayFragmentHome() {

        fragment_count += 1;

        if (fragment_home == null) {
            fragment_home = Fragment_Home.newInstance();
        }

        if (fragment_home.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_home).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").addToBackStack("fragment_home").commit();
        }

    }

    public void DisplayFragmentMain() {

        if (fragment_main == null) {
            fragment_main = Fragment_Main.newInstance();
        }

        if (fragment_main.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_main).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();
        }
        if (fragment_home != null&& fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(0);
        }
    }

    public void DisplayFragmentCalculateprice() {
fragment_count+=1;
        if (fragment_calculate_price == null) {
            fragment_calculate_price = Fragment_Calculate_price.newInstance();
        }

        if (fragment_calculate_price.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_calculate_price).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_calculate_price, "fragment_calculate_price").addToBackStack("fragment_calculate_price").commit();
        }
        if(fragment_home!=null&& fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(1);
        }
    }
    public void DisplayFragmentComputrizedprice() {
        fragment_count+=1;
        if (fragment_computrized_price == null) {
            fragment_computrized_price = Fragment_Computrized_Price.newInstance();
        }

        if (fragment_computrized_price.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_computrized_price).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_computrized_price, "fragment_computrized_price").addToBackStack("fragment_computrized_price").commit();
        }

    }
    public void DisplayFragmentTrackTheShipment() {
        fragment_count+=1;


        fragment_track_the_shipment = Fragment_Track_The_Shipment.newInstance(2);


        if (fragment_track_the_shipment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_track_the_shipment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_track_the_shipment, "fragment_track_the_shipment").addToBackStack("fragment_track_the_shipment").commit();
        }
        if(fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(2);
        }
    }
    public void DisplayFragmentProfile() {

        fragment_count += 1;

        if (fragment_profile == null) {
            fragment_profile = Fragment_Profile.newInstance();
        }

        if (fragment_profile.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_profile).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_profile, "fragment_profile").addToBackStack("fragment_profile").commit();
        }

    }
    public void DisplayFragmentPayments() {

        fragment_count += 1;

        if (fragment_payments == null) {
            fragment_payments = Fragment_Payments.newInstance();
        }

        if (fragment_payments.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_payments).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_payments, "fragment_payments").addToBackStack("fragment_payments").commit();
        }

    }
    public void DisplayFragmentshipments() {

        fragment_count += 1;

        if (fragment_ٍShipments == null) {
            fragment_ٍShipments = Fragment_ٍShipments.newInstance();
        }

        if (fragment_ٍShipments.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍShipments).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍShipments, "fragment_ٍShipments").addToBackStack("fragment_ٍShipments").commit();
        }

    }
    public void DisplayFragmentLanguage() {

        fragment_count += 1;

        if (fragment_language == null) {
            fragment_language = Fragment_Language.newInstance();
        }

        if (fragment_language.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_language).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_language, "fragment_language").addToBackStack("fragment_language").commit();
        }

    }

    public void DisplayFragmentEmailAddress() {

        fragment_count += 1;

        if (fragment_email_address == null) {
            fragment_email_address = Fragment_Email_Address.newInstance();
        }

        if (fragment_email_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_email_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_email_address, "fragment_email_address").addToBackStack("fragment_email_address").commit();
        }

    }

    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {
        if(fragment_home!=null&&fragment_home.mDrawer.isDrawerOpen(GravityCompat.START)){

                fragment_home.mDrawer.closeDrawers();

        }
        else{
        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();
        } else {
            if (fragment_home != null && fragment_home.isVisible()) {
                if (fragment_main != null && fragment_main.isVisible()) {
                    finish();
                } else {
                    DisplayFragmentMain();
                }
            } else {
                DisplayFragmentHome();
            }
        }}
    }

    public void RefreshActivity(String selected_language) {
        Paper.book().write("lang", selected_language);
        Language.setNewLocale(this, selected_language);

        Intent intent = getIntent();
        finish();

        startActivity(intent);


    }

    public void startscdeule() {
        Intent intent=new Intent(Home_Activity.this, Scedule_Activity.class);
        startActivity(intent);
    }


}

