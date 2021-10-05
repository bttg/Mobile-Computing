package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseForRecordOperation extends SQLiteOpenHelper {
    public DatabaseForRecordOperation(@Nullable Context context) {
        super(context, "yiweiguo.db", null, 1);
    }

    //The method of creating a database will be called only when the project is run for the first time
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create a table of related types

    }

    //When the database version changes during the update, this method will be called
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
