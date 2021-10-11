package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String nickname = intent.getStringExtra("nickname");
        String email = intent.getStringExtra("email");
        String id = intent.getStringExtra("id");
        TextView welcome_msg = (TextView) findViewById(R.id.welcome_msg);
        welcome_msg.setText(id);
    }
}