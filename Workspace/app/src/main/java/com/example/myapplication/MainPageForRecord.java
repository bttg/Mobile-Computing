package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPageForRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_for_record);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_recordone:
                Intent intent2 = new Intent(this, RecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_btn_more_function:

                break;
        }
    }


}