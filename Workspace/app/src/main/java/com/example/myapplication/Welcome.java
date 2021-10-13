package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

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

        Button btn_settings = findViewById(R.id.Btn_Settings);
        TextView welcome_msg = (TextView) findViewById(R.id.welcome_msg);

        welcome_msg.setText(nickname);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, Settings.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });


        String id = intent.getStringExtra("id");
        TextView welcome_msg = (TextView) findViewById(R.id.welcome_msg);
        welcome_msg.setText(id);

    }
}