package com.creativeshare.sals.Activities_Fragments.Home.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_Add_Credit_Cart;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_calculate_price.fragment_calculate.Fragment_Calculate_price;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_calculate_price.fragment_calculate_result.Fragment_Computrized_Price;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_Email_Address;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.frgment_more.fragment_help.Fragment_Help_Advice;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Home;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.frgment_more.fragment_help.fragment_ticket.Fragment_Issue_Catogry;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_Language;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.frgment_more.Fragment_Payments;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_Profile;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.Fragment_Main;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.frgment_more.fragment_help.Fragment_Question;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.frgment_more.fragment_help.fragment_ticket.Fragment_Ticket;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_Update_Address;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_ٍEdit_Name;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_ٍEdit_Phone;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_profile.Fragment_ٍMy_Address;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_shipment.fragment_shipment_details.Fragment_ٍShipment_Details;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_shipment.fragment_shipment_orders.Fragment_ٍShipments;
import com.creativeshare.sals.Activities_Fragments.Registration.Activity.Register_Activity;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.fragment_services.Fragment_Service_Centers;
import com.creativeshare.sals.Activities_Fragments.Registration.Fragments.fragment_track.Fragment_Track_The_Shipment;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Fragments.fragment_shipping_detials.Fragment_Add_Address;
import com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_calculate_price.fragment_calculate.Fragment_Shipping_Dementions;
import com.creativeshare.sals.Activities_Fragments.customer_service_activity.Customer_service_Activity;
import com.creativeshare.sals.Language.Language;
import com.creativeshare.sals.R;
import com.creativeshare.sals.Share.Common;
import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.Orders_Model;
import com.creativeshare.sals.models.Questions_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.models.Visit_Model;
import com.creativeshare.sals.preferences.Preferences;
import com.creativeshare.sals.remote.Api;
import com.creativeshare.sals.tags.Tags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_Calculate_price fragment_calculate_price;
    private Fragment_Shipping_Dementions fragment_shipping_dementions;

    private Fragment_Computrized_Price fragment_computrized_price;
    private Fragment_Profile fragment_profile;
    private Fragment_Help_Advice fragment_help_advice;

    private Fragment_Ticket fragment_ticket;
    private Fragment_ٍShipment_Details fragment_ٍShipment_details;
    private Fragment_Issue_Catogry fragment_issue_catogry;
    private Fragment_Question fragment_question;
    private Fragment_Payments fragment_payments;
    private Fragment_ٍShipments fragment_ٍShipments;
    private Fragment_Service_Centers fragment_service_centers;
    private Fragment_Language fragment_language;
    private Fragment_Email_Address fragment_email_address;
    private Fragment_ٍEdit_Name fragment_ٍEdit_name;
    private Fragment_ٍMy_Address fragment_ٍMy_address;
    private Fragment_Add_Credit_Cart fragment_add_credit_cart;
    private Fragment_ٍEdit_Phone fragment_ٍEdit_phone;
    private Fragment_Add_Address fragment_add_address;
    private Fragment_Update_Address fragment_update_address;
    private Fragment_Track_The_Shipment fragment_track_the_shipment;
    private int fragment_count = 0;
    private String current_lang;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Preferences.getInstance().getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Paper.init(this);
        preferences = Preferences.getInstance();
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = this.getSupportFragmentManager();

        userModel = preferences.getUserData(this);
        String visitTime = preferences.getVisitTime(this);
        Calendar calendar = Calendar.getInstance();
        long timeNow = calendar.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date = dateFormat.format(new Date(timeNow));

        if (!date.equals(visitTime)) {
            addVisit(date);
        }
        if (savedInstanceState == null) {
            DisplayFragmentHome();
            DisplayFragmentMain();


        }
        if (userModel != null) {
            updateToken();
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
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(0);
        }
    }

    public void DisplayFragmentCalculateprice() {
        fragment_count += 1;
        fragment_calculate_price = Fragment_Calculate_price.newInstance();


        if (fragment_calculate_price.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_calculate_price).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_calculate_price, "fragment_calculate_price").addToBackStack("fragment_calculate_price").commit();
        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(1);
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

    public void DisplayFragmentComputrizedprice() {
        fragment_count += 1;

        fragment_computrized_price = Fragment_Computrized_Price.newInstance();


        if (fragment_computrized_price.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_computrized_price).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_computrized_price, "fragment_computrized_price").addToBackStack("fragment_computrized_price").commit();
        }

    }

    public void DisplayFragmentTrackTheShipment() {
        fragment_count += 1;


        fragment_track_the_shipment = Fragment_Track_The_Shipment.newInstance(2);


        if (fragment_track_the_shipment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_track_the_shipment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_track_the_shipment, "fragment_track_the_shipment").addToBackStack("fragment_track_the_shipment").commit();
        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(2);
        }
    }

    public void DisplayFragmentProfile() {

        fragment_count += 1;


            fragment_profile = Fragment_Profile.newInstance();


        if (fragment_profile.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_profile).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_profile, "fragment_profile").addToBackStack("fragment_profile").commit();
        }

    }

    public void DisplayFragmentHelp() {

        fragment_count += 1;


            fragment_help_advice = Fragment_Help_Advice.newInstance();


        if (fragment_help_advice.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_help_advice).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_help_advice, "fragment_help_advice").addToBackStack("fragment_help_advice").commit();
        }

    }
    public void DisplayFragmentTicket() {

        fragment_count += 1;

            fragment_ticket = Fragment_Ticket.newInstance();


        if (fragment_ticket.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ticket).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ticket, "fragment_ticket").addToBackStack("fragment_ticket").commit();
        }

    }
    public void DisplayFragmentshipmentdetails(Orders_Model.Orders.Data id) {

        fragment_count += 1;

        fragment_ٍShipment_details = Fragment_ٍShipment_Details.newInstance(id);


        if (fragment_ٍShipment_details.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍShipment_details).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍShipment_details, "fragment_ٍShipment_details").addToBackStack("fragment_ٍShipment_details").commit();
        }

    }
    public void DisplayFragmentIssue() {

        fragment_count += 1;

            fragment_issue_catogry = Fragment_Issue_Catogry.newInstance();


        if (fragment_issue_catogry.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_issue_catogry).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_issue_catogry, "fragment_issue_catogry").addToBackStack("fragment_issue_catogry").commit();
        }

    }
    public void DisplayFragmentQuestion(Questions_Model.Faqs faqs) {

        fragment_count += 1;


            fragment_question = Fragment_Question.newInstance(faqs);


        if (fragment_question.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_question).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_question, "fragment_question").addToBackStack("fragment_question").commit();
        }

    }
    public void DisplayFragmentPayments() {

        fragment_count += 1;


            fragment_payments = Fragment_Payments.newInstance();


        if (fragment_payments.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_payments).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_payments, "fragment_payments").addToBackStack("fragment_payments").commit();
        }

    }

    public void DisplayFragmentshipments() {

        fragment_count += 1;


            fragment_ٍShipments = Fragment_ٍShipments.newInstance();


        if (fragment_ٍShipments.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍShipments).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍShipments, "fragment_ٍShipments").addToBackStack("fragment_ٍShipments").commit();
        }

    }

    public void DisplayFragmentServiceCenters() {
        fragment_count += 1;


        fragment_service_centers = Fragment_Service_Centers.newInstance(2);


        if (fragment_service_centers.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_service_centers).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_service_centers, "fragment_service_centers").addToBackStack("fragment_service_centers").commit();
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

            fragment_email_address = Fragment_Email_Address.newInstance();


        if (fragment_email_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_email_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_email_address, "fragment_email_address").addToBackStack("fragment_email_address").commit();
        }

    }

    public void DisplayFragmentEditname() {
        fragment_count += 1;

            fragment_ٍEdit_name = Fragment_ٍEdit_Name.newInstance();


        if (fragment_ٍEdit_name.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍEdit_name).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍEdit_name, "fragment_ٍEdit_name").addToBackStack("fragment_ٍEdit_name").commit();
        }
    }

    public void DisplayFragmentMyaddress() {
        fragment_count += 1;

            fragment_ٍMy_address = Fragment_ٍMy_Address.newInstance();


        if (fragment_ٍMy_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍMy_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍMy_address, "fragment_ٍMy_address").addToBackStack("fragment_ٍMy_address").commit();
        }
    }

    public void DisplayFragmentAddcreditCard() {
        fragment_count += 1;

            fragment_add_credit_cart = Fragment_Add_Credit_Cart.newInstance();


        if (fragment_add_credit_cart.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_add_credit_cart).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_add_credit_cart, "fragment_add_credit_cart").addToBackStack("fragment_add_credit_cart").commit();
        }
    }

    public void DisplayFragmentEditphone() {
        fragment_count += 1;

            fragment_ٍEdit_phone = Fragment_ٍEdit_Phone.newInstance();


        if (fragment_ٍEdit_phone.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_ٍEdit_phone).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_ٍEdit_phone, "fragment_ٍEdit_phone").addToBackStack("fragment_ٍEdit_phone").commit();
        }
    }

    public void DisplayFragmentAddaddress() {
        fragment_count += 1;


        fragment_add_address = Fragment_Add_Address.newInstance(1);


        if (fragment_add_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_add_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_add_address, "fragment_add_address").addToBackStack("fragment_add_address").commit();
        }
    }

    public void DisplayFragmentUpdateaddress(int address_id) {
        fragment_count += 1;


        fragment_update_address = Fragment_Update_Address.newInstance(address_id);


        if (fragment_update_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_update_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_update_address, "fragment_update_address").addToBackStack("fragment_update_address").commit();
        }
    }

    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {
        if (fragment_home != null && fragment_home.mDrawer.isDrawerOpen(GravityCompat.START)) {

            fragment_home.mDrawer.closeDrawers();


        } else {
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
            }
        }
    }

    public void RefreshActivity(String selected_language) {
        preferences.create_update_language(this, selected_language);
        Paper.book().write("lang", selected_language);
        Language.setNewLocale(this, selected_language);

        Intent intent = getIntent();
        finish();

        startActivity(intent);


    }

    public void startscdeule(int param) {
        if(userModel.getUser().getFirst_name()!=null&&userModel.getUser().getLast_name()!=null||userModel.getUser().getEmail()!=null){
        Intent intent = new Intent(Home_Activity.this, Scedule_Activity.class);
        intent.putExtra("param", param);
        startActivityForResult(intent,1);}
        else {
            Common.CreateSignAlertDialog(this,getResources().getString(R.string.complete_profile));
        }
    }


    public void Logout() {
        userModel = null;
        preferences.create_update_userdata(this, userModel);
        preferences.create_update_session(this, Tags.session_logout);
        Intent intent = new Intent(Home_Activity.this, Register_Activity.class);
        startActivity(intent);
        finish();
    }

    private void addVisit(final String timeNow) {
        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService()
                .updateVisit("1", timeNow)
                .enqueue(new Callback<Visit_Model>() {
                    @Override
                    public void onResponse(Call<Visit_Model> call, Response<Visit_Model> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            try {
                                preferences.saveVisitTime(Home_Activity.this, timeNow);
                            } catch (Exception e) {
                               // e.printStackTrace();
                            }
                            // Log.e("msg",response.body().toString());

                        } else {
                            try {
                                Log.e("error_code", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Visit_Model> call, Throwable t) {
                        dialog.dismiss();
                        try {
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {
                        }
                    }
                });

    }

    public void upadateprefrece(UserModel body) {
        body.setToken(userModel.getToken());
        userModel=body;
        if (fragment_profile != null) {
            fragment_profile.upadateprefrece(body);
        }
    }

    public void updatedata(Address_Model body) {
        if (fragment_ٍMy_address != null && fragment_ٍMy_address.isAdded()) {
            fragment_ٍMy_address.updatedata(body);
        }
    }
    private void updateToken() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            //Log.e("s",token);
                            Api.getService()
                                    .updateToken("Bearer"+" "+userModel.getToken(), token)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                            if (response.isSuccessful()) {
                                                try {
                                                    Log.e("Success", "token updated");
                                                } catch (Exception e) {
                                                  //  e.printStackTrace();
                                                }
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            try {
                                                Log.e("Error", t.getMessage());
                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void setid(Orders_Model.Orders.Data id, int data) {
        if(fragment_ticket!=null&&fragment_ticket.isAdded()){
            fragment_ticket.setid(id.getId(),data);
            Back();
        }
        else {
            DisplayFragmentshipmentdetails(id);
        }

    }

    public void setcatid(int id) {
        if(fragment_ticket!=null){
            fragment_ticket.setcatid(id);
            Back();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.updateBottomNavigationPosition(0);
            }
            if(resultCode == RESULT_OK) {
              DisplayFragmentshipments();
            }
        }
    }

    public void startcustomactivity() {
        Intent intent = new Intent(Home_Activity.this, Customer_service_Activity.class);
        startActivity(intent);

    }
}

