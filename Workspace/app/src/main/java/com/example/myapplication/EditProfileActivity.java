package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity
{
    public static int id;
    public static String newName;
    public static String newPass;
    public static String newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        id = Integer.valueOf(intent.getStringExtra("userID"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt_profile);
    }

    public void btnSaveProfile(View v)
    {
        //when click "save" button, upload edited information to the server
        EditText etNewName = findViewById(R.id.edit_nickname);
        EditText etNewPass = findViewById(R.id.edit_password);
        EditText etNewEmail = findViewById(R.id.edit_email);

        newName = etNewName.getText().toString();
        newPass = etNewPass.getText().toString();
        newEmail = etNewEmail.getText().toString();
        sendRequestWithOkhttp(id);
        Log.v("new nickname",newName);
        Log.v("new password",newPass);
        Log.v("new email",newEmail);

        Intent intent = new Intent(this,MainPageForRecord.class);
        intent.putExtra("newNickname",newName);
        startActivity(intent);
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
                    ProfileRequest requestbody = new ProfileRequest();
                    requestbody.setId(username);
                    requestbody.setNewEmail(newEmail);
                    requestbody.setNewName(newName);
                    requestbody.setNewPassword(newPass);
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbody)));// modify
                    OkHttpClient client = new OkHttpClient();
                    Log.d("post","3");
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/modifyUser")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("get", responseData);
                    Status resultPie = new Gson().fromJson(responseData, Status.class);
                    int status = resultPie.getStatus();
                    Log.d("status", Integer.toString(status));

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private String parseRequestBody(ProfileRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }



}