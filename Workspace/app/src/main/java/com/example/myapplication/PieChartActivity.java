package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartActivity extends AppCompatActivity
{
    private static String userID;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = findViewById(R.id.pieChart_view);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        

        //test only: get the pie data from arraylist

        String[][] testArray = new String[5][2];
        testArray[0][0] = "MaGe";
        testArray[0][1] = "18";
        testArray[1][0] = "ZhuGe";
        testArray[1][1] = "19";
        testArray[2][0] = "GuoGe";
        testArray[2][1] = "20";
        testArray[3][0] = "self";
        testArray[3][1] = "21";
        testArray[4][0] = "LuoGe";
        testArray[4][1] = "100.56";

        //add each data into the PieEntry
        List<PieEntry> entries = new ArrayList<>();
        for(int i=0; i< testArray.length; i++)
        {
            entries.add(new PieEntry(Float.parseFloat(testArray[i][1]), testArray[i][0]));
        }

        PieDataSet set = new PieDataSet(entries, "Election Results");

        //set some default colors
        set.addColor(Color.parseColor("#F60000"));
        set.addColor(Color.parseColor("#F59201"));
        set.addColor(Color.parseColor("#F6D300"));
        set.addColor(Color.parseColor("#8DF600"));
        set.addColor(Color.parseColor("#00F0F6"));
        set.addColor(Color.parseColor("#0064F6"));
        set.addColor(Color.parseColor("#6900F6"));
        set.addColor(Color.parseColor("#F600D9"));


        //set text size
        set.setValueTextSize(20f);



        PieData data = new PieData(set);

        pieChart.setData(data);

        //set the description
        pieChart.getDescription().setPosition(500,100);
        pieChart.getDescription().setText("");
        pieChart.getDescription().setTextSize(20f);

        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setTextSize(15f);
        pieChart.getLegend().setDrawInside(false);


        pieChart.invalidate(); // refresh


        //   pieChart.getLegend().setDirection(Legend.LegendDirection.);

        //    showPieChart();


    }

    /*
    private void initPieChart()
    {
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"));

    }      */


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