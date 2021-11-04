package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Function extends AppCompatActivity
{
    private static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Log.v("test","sad");
        setContentView(R.layout.activity_function);
        Intent intent = getIntent();
        userID = intent.getStringExtra("username");
        Button email = (Button) findViewById(R.id.EmailMe);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkhttp(userID);
            }
        });

    }

    public void btnChgProfile(View v)
    {
        //   setContentView(R.layout.activity_edit_profile);
        Log.v("check_profile:", "Yes");
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);

    }

    public void btnShowPieChart(View v)
    {
        //   setContentView(R.layout.activity_showpiechart);

//        sendRequestWithOkhttp(5);
        Intent intent = new Intent(this, PieChartActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }


    public void sendRequestWithOkhttp(String userId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    Status requestbdy = new Status();
                    requestbdy.setCode(Integer.valueOf(userId));

                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbdy)));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/emailMe")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String parseRequestBody(Status request)
    {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }



}