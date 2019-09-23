package com.creativeshare.sals.activities_fragments.secdule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation.Fragment_Policy;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_shipping_detials.Fragment_Additional_services;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation.Fragment_Confirmation;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_paymen_type_confirmation.Fragment_Delivry_Chooser;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_shipping_detials.Fragment_Add_Address;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_recepit.Fragment_Map;
import com.creativeshare.sals.activities_fragments.secdule.fragments.Fragment_Secdule;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_shipping_detials.Fragment_Shipping_Dementions;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_shipping_detials.Fragment_Shipping_Detials;
import com.creativeshare.sals.activities_fragments.secdule.fragments.fragment_recepit.Fragment_The_Recepit;
import com.creativeshare.sals.language.Language;
import com.creativeshare.sals.R;
import com.creativeshare.sals.models.Dementions_Model;
import com.creativeshare.sals.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class Scedule_Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment_Secdule fragment_secdule;
    private Fragment_Additional_services fragment_additional_services;
    private Fragment_Shipping_Dementions fragment_shipping_dementions;
    private Fragment_Add_Address fragment_search_for_address;
    private Fragment_The_Recepit fragment_the_recepit;
    private Fragment_Shipping_Detials fragment_shipping_detials;
    private Fragment_Delivry_Chooser fragment_delivry_chooser;
    private Fragment_Confirmation fragment_confirmation;
    private Fragment_Policy fragment_policy;
    private Fragment_Map fragment_map;

    private int fragment_count = 0;
    private String current_lang;
    public int param;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(Language.updateResources(base, Preferences.getInstance().getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secdule);
        param=getIntent().getIntExtra("param",-1);
        Paper.init(this);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = this.getSupportFragmentManager();

        if (savedInstanceState == null) {
            DisplayFragmentSecdule();
            DisplayFragmentTherecipet();
        }

    }


    public void DisplayFragmentSecdule() {

        fragment_count += 1;

        if (fragment_secdule == null) {
            fragment_secdule = Fragment_Secdule.newInstance();
        }

        if (fragment_secdule.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_secdule).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_secdule, "fragment_secdule").addToBackStack("fragment_secdule").commit();
        }

    }
    public void DisplayFragmentPolicy() {

        fragment_count += 1;

        if (fragment_policy == null) {
            fragment_policy = Fragment_Policy.newInstance();
        }

        if (fragment_policy.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_policy).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_policy, "fragment_policy").addToBackStack("fragment_policy").commit();
        }

    }

    public void DisplayFragmentAdditionalservices() {
        fragment_count += 1;

        if (fragment_additional_services == null) {
            fragment_additional_services = Fragment_Additional_services.newInstance();
        }

        if (fragment_additional_services.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_additional_services).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_additional_services, "fragment_additional_services").addToBackStack("fragment_additional_services").commit();
        }
    }
    public void DisplayFragmentShippingDimentions() {
        fragment_count += 1;


            fragment_shipping_dementions = Fragment_Shipping_Dementions.newInstance();


        if (fragment_shipping_dementions.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_shipping_dementions).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_shipping_dementions, "fragment_shipping_dementions").addToBackStack("fragment_shipping_dementions").commit();
        }
    }
    public void DisplayFragmentSearchforaddress() {
        fragment_count += 1;

            fragment_search_for_address = Fragment_Add_Address.newInstance(2);


        if (fragment_search_for_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_search_for_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_search_for_address, "fragment_add_address").addToBackStack("fragment_add_address").commit();
        }
    }

    public void DisplayFragmentTherecipet() {

        if (fragment_the_recepit == null) {
            fragment_the_recepit = Fragment_The_Recepit.newInstance();
        }
        if (fragment_confirmation != null && fragment_confirmation.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_confirmation).commit();

        }
        if (fragment_delivry_chooser != null && fragment_delivry_chooser.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_delivry_chooser).commit();

        }
        if (fragment_shipping_detials != null && fragment_shipping_detials.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_shipping_detials).commit();

        }
        if (fragment_the_recepit.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_the_recepit).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_the_recepit, "fragment_the_recepit").addToBackStack("fragment_the_recepit").commit();
        }
        if (fragment_secdule != null && fragment_secdule.isAdded()) {
            fragment_secdule.update2(0);
        }
    }

    public void DisplayFragmentdelivrychooser() {

        if (fragment_delivry_chooser == null) {
            fragment_delivry_chooser = Fragment_Delivry_Chooser.newInstance();
        }
        if (fragment_confirmation != null && fragment_confirmation.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_confirmation).commit();

        }
        if (fragment_shipping_detials != null && fragment_shipping_detials.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_shipping_detials).commit();

        }
        if (fragment_the_recepit != null && fragment_the_recepit.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_the_recepit).commit();

        }
        if (fragment_delivry_chooser.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_delivry_chooser).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_delivry_chooser, "fragment_delivry_chooser").addToBackStack("fragment_delivry_chooser").commit();
        }
        if (fragment_secdule != null && fragment_secdule.isAdded()) {
            fragment_secdule.update2(1);
        }
    }
    public void DisplayFragmentshippingdetilas() {

        if (fragment_shipping_detials == null) {
            fragment_shipping_detials = Fragment_Shipping_Detials.newInstance();
        }
        if (fragment_confirmation != null && fragment_confirmation.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_confirmation).commit();

        }
        if (fragment_delivry_chooser != null && fragment_delivry_chooser.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_delivry_chooser).commit();

        }
        if (fragment_the_recepit != null && fragment_the_recepit.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_the_recepit).commit();

        }
        if (fragment_shipping_detials.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_shipping_detials).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_shipping_detials, "fragment_shipping_detials").addToBackStack("fragment_shipping_detials").commit();
        }

    }

    public void DisplayFragmentconfirmation() {

        if (fragment_confirmation == null) {
            fragment_confirmation = Fragment_Confirmation.newInstance();
        }
        if (fragment_the_recepit != null && fragment_the_recepit.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_the_recepit).commit();

        }
        if (fragment_delivry_chooser != null && fragment_delivry_chooser.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_delivry_chooser).commit();

        }
        if (fragment_shipping_detials != null && fragment_shipping_detials.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_shipping_detials).commit();

        }
        if (fragment_confirmation.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_confirmation).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_confirmation, "fragment_confirmation").addToBackStack("fragment_confirmation").commit();
        }
        if (fragment_secdule != null && fragment_secdule.isAdded()) {
            fragment_secdule.update2(2);
        }

    }


    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {
        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();
        } else {
            if (fragment_secdule != null && fragment_secdule.isVisible()) {
                if (fragment_confirmation != null && fragment_confirmation.isVisible()) {

                        fragment_secdule.update(1);
                        DisplayFragmentdelivrychooser();


                }
                else if(fragment_delivry_chooser!=null&&fragment_delivry_chooser.isVisible()){
                    DisplayFragmentshippingdetilas();
                }
                else if (fragment_shipping_detials != null && fragment_shipping_detials.isVisible()) {
                    fragment_secdule.update(0);
                    DisplayFragmentTherecipet();
                } else {
                    finish();
                }
            } else {
                DisplayFragmentSecdule();
            }
        }
    }


    public void updatedata(String body) {
        if(fragment_the_recepit!=null&&fragment_the_recepit.isAdded()){
            fragment_the_recepit.updateaddreess(body);
        }
    }

    public void sentdemminssion(Dementions_Model dementions_model) {
        if(fragment_shipping_detials!=null&&fragment_shipping_detials.isAdded()){
            fragment_shipping_detials.adddeminssion(dementions_model);
        }
    }
    public void DisplayFragmentMap()
    {
        fragment_count+=1;


            fragment_map = Fragment_Map.newInstance();



        if (fragment_map.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_map).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_map, "fragment_map").addToBackStack("fragment_map").commit();
        }



    }
    public void display() {
        Intent intent = new Intent();
       // intent.putExtra("editTextValue", "value_here")
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setaddress(String address) {
        if(fragment_shipping_detials!=null&&fragment_shipping_detials.isAdded())
        {
            fragment_shipping_detials.setaddressto(address);
        }
    }
}

