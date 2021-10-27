package com.example.mage;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity
{

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v("test","sad");
        setContentView(R.layout.activity_second);

        pieChart = findViewById(R.id.pieChart_view);


        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        PieData data = new PieData(set);

        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        //showPieChart();

    }

    public void btnChgProfile(View v)
    {
        setContentView(R.layout.activity_changename);
    }

    public void btnGoToMainPage(View v)
    {
        setContentView(R.layout.activity_second);
    }

    public void btnSaveProfile(View v)
    {
        EditText etNewName = findViewById(R.id.edit_nickname);
        EditText etNewPass = findViewById(R.id.edit_password);
        EditText etNewEmail = findViewById(R.id.edit_email);

        String newName = etNewName.getText().toString();
        String newPass = etNewPass.getText().toString();
        String newEmail = etNewEmail.getText().toString();

        Log.v("new nickname",newName);
        Log.v("new password",newPass);
        Log.v("new email",newEmail);
    }


    private void showPieChart()
    {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Toys",200);
        typeAmountMap.put("Snacks",100);
        typeAmountMap.put("Clothes",200);
        typeAmountMap.put("Stationary",100);


        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#F60000"));
        colors.add(Color.parseColor("#F59201"));
        colors.add(Color.parseColor("#F6D300"));
        colors.add(Color.parseColor("#8DF600"));
        colors.add(Color.parseColor("#00F0F6"));
        colors.add(Color.parseColor("#0064F6"));
        colors.add(Color.parseColor("#6900F6"));
        colors.add(Color.parseColor("#F600D9"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet())
        {
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(20f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}