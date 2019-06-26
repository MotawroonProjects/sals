package com.creativeshare.sals.Activities_Fragments.Home.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Activities_Fragments.Secdule.Activity.Scedule_Activity;
import com.creativeshare.sals.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import io.paperdb.Paper;


public class Fragment_Home extends Fragment {

    private AHBottomNavigation ah_bottom_nav;
    public DrawerLayout mDrawer;
    private NavigationView nvView;
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);

        mDrawer = view.findViewById(R.id.drawer_layout);
        nvView=view.findViewById(R.id.nvView);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawer.isDrawerOpen(GravityCompat.START)) {
                    //drawer is open
                    mDrawer.closeDrawers();
                }
                else{
                activity.Back();}
            }
        });
        ah_bottom_nav = view.findViewById(R.id.ah_bottom_nav);


        setUpBottomNavigation();
        ah_bottom_nav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:

                        activity.startscdeule(0);
                        break;
                    case 1:

                        activity.DisplayFragmentCalculateprice();
                        break;
                    case 2:
                        activity.DisplayFragmentTrackTheShipment();

                        break;
                    case 3:

                        mDrawer.openDrawer(GravityCompat.START);
                        updateBottomNavigationPosition(3);
                        break;
                }
                return false;
            }
        });
        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                switch (id){
                    case R.id.nav_shipping_details:
                        mDrawer.closeDrawers();
                        activity.DisplayFragmentshipments();
                        break;
                    case R.id.nav_payments:
                        mDrawer.closeDrawers();
                        activity.DisplayFragmentPayments();
                            break;
                    case R.id.nav_help_and_advice:
                        break;
                    case R.id.nav_personal_account:
                        mDrawer.closeDrawers();
                        activity.DisplayFragmentProfile();
                        break;
                    case R.id.nav_customer_service_center:

                            break;
                }
                return false;
            }
        });

    }


    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.schedule), R.drawable.shipment);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.price_calculate), R.drawable.calculate_price);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.tracking), R.drawable.trackingshipments);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.more), R.drawable.ic_more);

        ah_bottom_nav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ah_bottom_nav.setDefaultBackgroundColor(ContextCompat.getColor(activity, R.color.gray4));
        ah_bottom_nav.setTitleTextSizeInSp(14, 12);
        ah_bottom_nav.setForceTint(true);
        ah_bottom_nav.setAccentColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        ah_bottom_nav.setInactiveColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        ah_bottom_nav.addItem(item1);
        ah_bottom_nav.addItem(item2);
        ah_bottom_nav.addItem(item3);
        ah_bottom_nav.addItem(item4);


    }

    public void updateBottomNavigationPosition(int pos) {

        ah_bottom_nav.setCurrentItem(pos, false);
    }


}