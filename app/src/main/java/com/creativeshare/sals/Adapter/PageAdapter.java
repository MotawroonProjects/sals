package com.creativeshare.sals.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public PageAdapter(FragmentManager fm) {
        super(fm);
        fragmentList=new ArrayList<>();
    }
public void addfragments(List<Fragment> fragmentList){
        this.fragmentList.addAll(fragmentList);
}
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
