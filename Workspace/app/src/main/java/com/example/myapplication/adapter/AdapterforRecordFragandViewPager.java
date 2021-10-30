package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class AdapterforRecordFragandViewPager extends FragmentPagerAdapter {
    //Pass in the collection
    List<Fragment> listfragment;
    String [] fragmentName = {"Expense","Income"};
    public AdapterforRecordFragandViewPager(@NonNull FragmentManager fm, List<Fragment> listfragment) {
        super(fm);
        this.listfragment = listfragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listfragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentName[position];
    }
}
