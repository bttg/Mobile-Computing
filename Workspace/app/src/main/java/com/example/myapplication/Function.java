package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

//    public void sendRequestWithOkhttp(int userId)
//    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
//                try {
//                    DataVisualRequest requestbdy = new DataVisualRequest();
//                    requestbdy.setUserId(userId);
//
//                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbdy)));
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("http://139.180.180.99:8888/api/dataVisual")
//                            .post(requestBody)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
////                    Log.d("printout",responseData);
//
//                    Log.v("data is:", responseData);
//
//
//                    /*
//                    if(parseJson(responseData).get("status").equals("200")){
//                        ArrayList<JSONObject> dataArray = new ArrayList<JSONObject>();
//
//                        dataArray = (ArrayList<JSONObject>)parseJson(responseData).get("data");
//
//                        String id = (String) parseJson(responseData).get("id");
//                        String nickname = (String) parseJson(responseData).get("nickname");
//                        String email = (String) parseJson(responseData).get("email");
//                        Looper.prepare();
//                        Intent intent = new Intent(MainPageActivity.this, Welcome.class);
//                        intent.putExtra("username", username);
//                        intent.putExtra("nickname", nickname);
//                        intent.putExtra("email", email);
//                        intent.putExtra("id", id);
//                        startActivity(intent);
//                        Looper.loop();
//                    }
//                        */
//                }
//
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    /*
//    private Map parseJson(String json) {
//        Gson gson = new Gson();
//        LoginHandler result = gson.fromJson(json, LoginHandler.class);
//        if (result.getStatus() == 200) {
//            String nickname = result.getNickname();
//            String email = result.getEmail();
//            String id = String.valueOf(result.getID());
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("status", "200");
//            map.put("nickname", nickname);
//            map.put("email", email);
//            map.put("id", id);
//            return map;
//        }
//        else {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("status", String.valueOf(result.getStatus()));
//            return map;
//        }
//    }
//
//     */
//
//    private String parseRequestBody(DataVisualRequest request)
//    {
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        return json;
//    }



}