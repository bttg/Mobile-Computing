package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText username = (EditText) findViewById(R.id.uname_input);
        EditText password = (EditText) findViewById(R.id.upass_input);
        EditText nickname = (EditText) findViewById(R.id.nickname_input);
        EditText email = (EditText) findViewById(R.id.email_input);
        Button submitbtn = (Button) findViewById(R.id.submitbtn);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = username.getText().toString();
                String pass_word = password.getText().toString();
                String nick_name = nickname.getText().toString();
                String e_mail = email.getText().toString();
                if("".equals(user_name)){
                    Toast.makeText(SignUp.this, "Username can't be none!", Toast.LENGTH_SHORT).show();
                }
                if("".equals(pass_word)){
                    Toast.makeText(SignUp.this, "Password can't be none!", Toast.LENGTH_SHORT).show();
                }
                if("".equals(nick_name)){
                    Toast.makeText(SignUp.this, "Nickname can't be none!", Toast.LENGTH_SHORT).show();
                }
                if("".equals(e_mail)){
                    Toast.makeText(SignUp.this, "Email can't be none!", Toast.LENGTH_SHORT).show();
                }
                if(!user_name.isEmpty() && !pass_word.isEmpty() && !nick_name.isEmpty() && !e_mail.isEmpty()){
                    sendRequestWithOkhttp(user_name, nick_name, pass_word, e_mail);

                }
            }
        });
    }

    private void sendRequestWithOkhttp(String username, String nickname, String password, String email){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    SignRequest requestbdy = new SignRequest();
                    requestbdy.setAccount(username);
                    requestbdy.setpwd(password);
                    requestbdy.setnnm(nickname);
                    requestbdy.setemail(email);
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("account", username)
//                            .add("nickname", nickname)
//                            .add("password", password)
//                            .add("email", email)
//                            .build();
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(requestbdy)));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/signup")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("printout",responseData);
                    if(parseJson(responseData)==200){
                        Looper.prepare();
                        Toast.makeText(SignUp.this, "Success", Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();
                    }
                    else if (parseJson(responseData)==201){
                        Looper.prepare();
                        Toast.makeText(SignUp.this, "Failed: The username existed", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        Looper.prepare();
                        Toast.makeText(SignUp.this, "Failed: Unknown error, try again later", Toast.LENGTH_LONG).show();
                        finish();
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