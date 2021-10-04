package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapter.AdapterforRecordFragandViewPager;
import com.example.myapplication.recordactivity_fragment.ExpenditureFragment;
import com.example.myapplication.recordactivity_fragment.IncomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //Find control
        tabLayout = findViewById(R.id.recordactivity_imageview_tablayouts);
        viewPager = findViewById(R.id.recordactivity_imageview_viewpage);
        //Set the viewpager to load the page
        initializeViewPager();

    }

    private void initializeViewPager() {
        //Initialize the page collection
        List<Fragment> listfragment = new ArrayList<>();
        //Create income and expenditure interface, put it in Fragment
        ExpenditureFragment expenditureFragment = new ExpenditureFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        listfragment.add(expenditureFragment);
        listfragment.add(incomeFragment);
        //create adapter
        AdapterforRecordFragandViewPager fragandPagerAdapter = new AdapterforRecordFragandViewPager(getSupportFragmentManager(), listfragment);
        //set adapter
        viewPager.setAdapter(fragandPagerAdapter);
        //Associate tablayout and viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.recordactivity_imageview_back:
                finish();
                break;
        }
    }

}