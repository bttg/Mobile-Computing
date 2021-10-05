package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class DatabaseForRecordOperation extends SQLiteOpenHelper {
    public DatabaseForRecordOperation(@Nullable Context context) {
        super(context, "yiweiguo.db", null, 1);
    }


    //The method of creating a database will be called only when the project is run for the first time
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create a table of related types
        String sql = "create table typetb(identity integer primary key autoincrement, image_name varchar(20),id_for_notselected integer,id_for_selected integer,expenseorincome integer)";
        sqLiteDatabase.execSQL(sql);
        addRecordInfor(sqLiteDatabase);
    }


    private void addRecordInfor(SQLiteDatabase sqLiteDatabase) {
        //Insert elements into typetb
        String sqlite = "insert into typetb (image_name,id_for_notselected,id_for_selected,expenseorincome) values (?,?,?,?)";
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Others", R.mipmap.ic_others,R.mipmap.ic_others_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Food", R.mipmap.ic_food,R.mipmap.ic_food_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Transport", R.mipmap.ic_transport,R.mipmap.ic_transport_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Shopping", R.mipmap.ic_shopping,R.mipmap.ic_shopping_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Clothes", R.mipmap.ic_clothes,R.mipmap.ic_clothes_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Daily", R.mipmap.ic_daily,R.mipmap.ic_daily_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Recreation", R.mipmap.ic_recreation,R.mipmap.ic_recreation_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Snacks", R.mipmap.ic_snacks,R.mipmap.ic_snacks_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Drink", R.mipmap.ic_drink,R.mipmap.ic_drink_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Learn", R.mipmap.ic_learn,R.mipmap.ic_learn_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Medical", R.mipmap.ic_medical,R.mipmap.ic_medical_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"House", R.mipmap.ic_house,R.mipmap.ic_house_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Fees", R.mipmap.ic_fees,R.mipmap.ic_fees_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Phone", R.mipmap.ic_phone,R.mipmap.ic_phone_fs,0});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Party", R.mipmap.ic_party,R.mipmap.ic_party_fs,0});

        sqLiteDatabase.execSQL(sqlite, new Object[] {"Others", R.mipmap.in_others,R.mipmap.in_others_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Salary", R.mipmap.in_salary,R.mipmap.in_salary_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Bonus", R.mipmap.in_bonus,R.mipmap.in_bonus_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Borrow", R.mipmap.in_borrow,R.mipmap.in_borrow_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Debt", R.mipmap.in_debt,R.mipmap.in_debt_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Interest", R.mipmap.in_interest,R.mipmap.in_interest_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Investment", R.mipmap.in_investment,R.mipmap.in_investment_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Transaction", R.mipmap.in_transaction,R.mipmap.in_transaction_fs,1});
        sqLiteDatabase.execSQL(sqlite, new Object[] {"Lottery", R.mipmap.in_lottery,R.mipmap.in_lottery_fs,1});
    }

    //When the database version changes during the update, this method will be called
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
