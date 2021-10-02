package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_button = (Button) findViewById(R.id.login);
        Button signup_button = (Button) findViewById(R.id.signup);
        EditText username_input = (EditText) findViewById(R.id.username_input);
        EditText password_input = (EditText) findViewById(R.id.password_input);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = username_input.getText().toString();
                        String password = password_input.getText().toString();
                        if (username.equals("")) {
                            Toast.makeText(MainActivity.this, "Username can't be none!", Toast.LENGTH_SHORT).show();
                        }
                        else if (password.equals("")) {
                            Toast.makeText(MainActivity.this, "Password can't be none!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sendRequestWithOkhttp(username, password);
                        }
                    }
                });
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void sendRequestWithOkhttp(String username, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    SignRequest requestbdy = new SignRequest();
                    requestbdy.setAccount(username);
                    requestbdy.setpwd(password);
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbdy)));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/login")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
//                    Log.d("printout",responseData);
                    if(parseJson(responseData)==200){
                        Looper.prepare();
                        Intent intent = new Intent(MainActivity.this, Welcome.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        Looper.loop();
                    }
                    else if (parseJson(responseData)==202){
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "Failed: Can't find the user.", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else if (parseJson(responseData) == 203){
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "Failed: Wrong password.", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int parseJson(String json) {
        Gson gson = new Gson();
        Status result = gson.fromJson(json, Status.class);
        return result.getStatus();
    }

    private String parseRequestBody(SignRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }
}