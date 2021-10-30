package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.database.ManageDatabase;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize the database
        ManageDatabase.initialDatabase(getApplicationContext());
    }

}
