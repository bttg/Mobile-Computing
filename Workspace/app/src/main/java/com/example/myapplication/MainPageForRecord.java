package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AdapterforAccount;
import com.example.myapplication.database.DataTypeForShow;
import com.example.myapplication.database.DataTypeForStore;
import com.example.myapplication.database.ManageDatabase;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainPageForRecord extends AppCompatActivity implements View.OnClickListener{

    ListView todaylistview; //Show recent income and expenditure
    List<DataHandler.Data> mDatas;
    AdapterforAccount adapter;
    int year;
    int month;
    int day;
    public static String inputusername;
    public static String nickname;
    public static DataHandler datahandler;

    public static globalVariable gv;

    //头布局相关控件
    View headerView;
    TextView topIncomeNumber;
    TextView topOutcomeNumber;
    ImageView topShowImageView;
    ImageButton maplabel;
    ImageButton morefuction;
    Button recordone;
    TextView topnickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_for_record);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=b8e23876");
        Intent intent = getIntent();
        inputusername = intent.getStringExtra("id");
        nickname =  intent.getStringExtra("nickname");

        if (inputusername!=null)
        {
            gv = new globalVariable(inputusername);
        }


        initTime();
        todaylistview = findViewById(R.id.main_lv);
        topnickname = findViewById(R.id.main_top_text_nickname);
        topnickname.setText(nickname);
        //添加ListView的头布局
        addListViewHeaderView();
        mDatas = new ArrayList<>();
//        sendRequestWithOkhttp(Integer.valueOf(inputusername));
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
        maplabel = headerView.findViewById(R.id.main_btn_map);
        morefuction = headerView.findViewById(R.id.main_btn_more_func);
        recordone = headerView.findViewById(R.id.main_record);
    }

    //The method that will be called when the activity gets the focus
    @Override
    protected void onResume() {
        super.onResume();

        if (inputusername==null)
        {
            inputusername = gv.getFixedID();
        }

        sendRequestWithOkhttp(Integer.valueOf(inputusername));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        if (nickname==null)
        {
            nickname = intent.getStringExtra("newNickname");
        }

        topnickname.setText(nickname);
        loadServerData();
        setTopTextviewShow();
    }

    private void setTopTextviewShow() {
        //TODO 修改获得数据库收入以及支出
//        float monthExpenditure = ManageDatabase.getmoneyonemonth(year,month,1);
        DecimalFormat df = new DecimalFormat("#.0");

        String monthExpenditure = df.format(datahandler.getExpenditure());
        String monthIncome = df.format(datahandler.getIncome());
//        double monthIncome = ManageDatabase.getmoneyonemonth(year,month,0);
        topOutcomeNumber.setText("$" + monthExpenditure);
        topIncomeNumber.setText("$" + monthIncome);
    }

    //用这个方法从服务器读取一个数据列表，数据类型包括：
    private void loadServerData() {
        //TODO 修改获得数据库列表内容
//        Log.d("handler",datahandler.getData().toString());

        List<DataHandler.Data> list  = datahandler.getData();
        Log.d("list", list.toString());

        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void sendRequestWithOkhttp(int username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                Log.d("before try","1");

                try {
                    Log.d("afterTry","2");
                    //SignRequest requestbdy = new SignRequest();
                    DataRequest requestbody = new DataRequest();
                    requestbody.setId(Integer.valueOf(inputusername));
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbody)));// modify
                    OkHttpClient client = new OkHttpClient();
                    Log.d("post","3");
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/fetchData")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("get", responseData);
                    DataHandler serverresult = new Gson().fromJson(responseData, DataHandler.class);
                    datahandler = new DataHandler(serverresult.getCode(),serverresult.getIncome(),serverresult.getExpenditure(),serverresult.getData());
                    int status = serverresult.getCode();
                    Log.d("status", Integer.toString(status));
                    List<DataHandler.Data> resdata = serverresult.getData();
                    for (int i=0; i<resdata.size();i++)
                    {
                        Log.d("moeny",Double.toString(resdata.get(i).getMoney()));
                        Log.d("tag",resdata.get(i).getTag());
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String parseRequestBody(DataRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.main_record:
                Intent intent2 = new Intent(this, RecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_btn_more_func:
                Intent intent3 = new Intent(this, Function.class);
                intent3.putExtra("username", inputusername);
                startActivity(intent3);
                break;
            case R.id.main_btn_map:
                Intent intent4 = new Intent(this,MapsActivity.class);
                startActivity(intent4);
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