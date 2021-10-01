package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.FormBody;
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
                try {
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", username)
                            .add("nickname", nickname)
                            .add("password", password)
                            .add("email", email)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/signup")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(parseJson(responseData)){
                        Toast.makeText(SignUp.this, "Success", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean parseJson(String json) {
        Gson gson = new Gson();
        Status result = gson.fromJson(json, Status.class);
        if (result.getStatus().equals("True")){
            return true;
        }
        else
            return false;
    }



}