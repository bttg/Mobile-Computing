package com.example.myapplication.database;

//The class responsible for managing the database
//Mainly operate on the contents of the table, CRUD

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageDatabase {

    private static SQLiteDatabase sqLiteDatabase;
    //Initialize database objects
    public static void initialDatabase(Context context){
        DatabaseForRecordOperation databaseForRecordOperation = new DatabaseForRecordOperation(context);
        sqLiteDatabase = databaseForRecordOperation.getWritableDatabase();
    }

    //Read the data in the database and write it to the memory collection
    //

    public static List<Record_TypeforEachOne> getRecord_TypeforEachOneList(int expenseorincome){
        List<Record_TypeforEachOne> list = new ArrayList<>();
        String sql = "select * from typetb where expenseorincome = " + expenseorincome;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToNext();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            int temp_identity = cursor.getColumnIndex("identity");
            int identity = cursor.getInt(temp_identity);
            int temp_image_name_index = cursor.getColumnIndex("image_name");
            String image_name = cursor.getString(temp_image_name_index);
            int temp_id_for_notselected = cursor.getColumnIndex("id_for_notselected");
            int id_for_notselected = cursor.getInt(temp_id_for_notselected);
            int temp_id_for_selected = cursor.getColumnIndex("id_for_selected");
            int id_for_selected = cursor.getInt(temp_id_for_selected);
            int temp_expenseorincome = cursor.getColumnIndex("expenseorincome");
            int expenseorincome1 = cursor.getInt(temp_expenseorincome);
            Record_TypeforEachOne record_typeforEachOne = new Record_TypeforEachOne(identity, id_for_notselected, id_for_selected, image_name, expenseorincome1);
            list.add(record_typeforEachOne);
        }
        return list;
    }

    public static float getmoneyonemonth(int year, int month, int kind){
        float total = 0.0f;

        return total;
    }


}
