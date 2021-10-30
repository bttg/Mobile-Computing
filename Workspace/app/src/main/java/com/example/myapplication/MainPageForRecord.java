package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AdapterforAccount;
import com.example.myapplication.database.DataTypeForStore;
import com.example.myapplication.database.ManageDatabase;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainPageForRecord extends AppCompatActivity implements View.OnClickListener{

    ListView todaylistview; //Show recent income and expenditure
    List<DataTypeForStore> mDatas;
    AdapterforAccount adapter;
    int year;
    int month;
    int day;
    public static String inputusername;

    //头布局相关控件
    View headerView;
    TextView topIncomeNumber;
    TextView topOutcomeNumber;
    ImageView topShowImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_for_record);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=b8e23876");
        Intent intent = getIntent();
        inputusername = intent.getStringExtra("username");




        initTime();
        todaylistview = findViewById(R.id.main_lv);
        //添加ListView的头布局
        addListViewHeaderView();
        mDatas = new ArrayList<>();
        //Set up the adapter, load each row of data into the list
        adapter = new AdapterforAccount(this,mDatas);
        todaylistview.setAdapter(adapter);

    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void addListViewHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_main_toplayout,null);
        todaylistview.addHeaderView(headerView);
        //Find available controls for header layout
        topIncomeNumber = headerView.findViewById(R.id.item_main_toplayout_income_number);
        topOutcomeNumber = headerView.findViewById(R.id.item_main_toplayout_expenditure_number);
        topShowImageView = headerView.findViewById(R.id.item_main_toplayout_hide);
    }

    //The method that will be called when the activity gets the focus
    @Override
    protected void onResume() {
        super.onResume();
        loadServerData();
        setTopTextviewShow();
    }

    private void setTopTextviewShow() {
        float monthExpenditure = ManageDatabase.getmoneyonemonth(year,month,1);
        float monthIncome = ManageDatabase.getmoneyonemonth(year,month,0);
        topOutcomeNumber.setText("$" + monthExpenditure);
        topIncomeNumber.setText("$" + monthIncome);

    }

    //用这个方法从服务器读取一个数据列表，数据类型包括：
    private void loadServerData() {
        List<DataTypeForStore> list  = new ArrayList<>();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.main_recordone:
                Intent intent2 = new Intent(this, RecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_btn_more_function:

                break;
        }
    }

    public String sendUsername(){
        return inputusername;
    }

    @Override
    public void onClick(View view) {

    }
}