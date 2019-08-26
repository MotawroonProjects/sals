package com.creativeshare.sals.Activities_Fragments.Home.Fragments.fragment_shipment.fragment_shipment_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.creativeshare.sals.Activities_Fragments.Home.Activity.Home_Activity;
import com.creativeshare.sals.Adapter.PageAdapter;
import com.creativeshare.sals.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_ٍShipments extends Fragment {
    private Home_Activity activity;
    private ImageView back_arrow;
    private String current_lang;
    private Fragment_Shipments_Sent fragment_shipments_sent;
    private Fragment_Shipments_Recived fragment_shipments_recived;
    private List<Fragment> fragmentList;
    private PageAdapter pageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Fragment_ٍShipments newInstance() {
        return new Fragment_ٍShipments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipments, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fragmentList = new ArrayList<>();
        activity = (Home_Activity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        back_arrow = view.findViewById(R.id.arrow);
        tabLayout = view.findViewById(R.id.tab_orders);
        viewPager = view.findViewById(R.id.pager);
        if (current_lang.equals("ar")) {
            back_arrow.setRotation(180.0f);
        }
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Back();
            }
        });
        intitfragmentspage();
        pageAdapter = new PageAdapter(activity.getSupportFragmentManager());
        pageAdapter.addfragments(fragmentList);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void intitfragmentspage() {
        fragment_shipments_sent = Fragment_Shipments_Sent.newInstance();
        fragment_shipments_recived = Fragment_Shipments_Recived.newInstance();
        fragmentList.add(fragment_shipments_sent);
        fragmentList.add(fragment_shipments_recived);
    }
}