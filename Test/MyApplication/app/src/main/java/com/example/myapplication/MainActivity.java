package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        Log.d("fail","fuken failed miserably:<");
    }

    public void disable(View v){
        findViewById(R.id.button).setEnabled(false);
        ((Button)findViewById(R.id.button)).setText("new new disabled");

        /*
        View myView = findViewById(R.id.button);
        myView.setEnabled(false);
        Button button = (Button) myView;
        button.setText("New Disabled");
        /*
        v.setEnabled(false);
        Log.d("success","button disabled!"); //can find in logcat
        Button button = (Button) v;
        button.setText("Disabled");
        */

    }

    public void handleText(View v){
        EditText t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((Button)findViewById(R.id.button)).setText("Hello "+input);
        Toast.makeText(this,input+" is an asshole", Toast.LENGTH_LONG).show();
        Log.d("info", input);
    }

    public void launchMainAccount(View v){
        //launch a new activity
        Intent i = new Intent(this, MainAccountActivity.class);
        String message = ((EditText)findViewById(R.id.source)).getText().toString();
        i.putExtra("AccName", message);
        startActivity(i);
    }

}