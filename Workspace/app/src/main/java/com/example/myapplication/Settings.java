package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Settings extends AppCompatActivity {



    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 99; //arbitrary choice
    // reference to UI elements
    TextView tv_lat, tv_lon, tv_accuracy, tv_sensor, tv_updates, tv_address;
    Button btn_bkp;

    Switch sw_locationsupdates, sw_gps;

    protected String addressString;
    //variable to remember if we are tracking location or nah
    boolean updateOn = false;

    //Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;

    //Location Request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent i = getIntent();
        String username = i.getStringExtra("Username");


        // give each UI variable a value

        btn_bkp = findViewById(R.id.btn_bkp);
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);

        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);

        //set all properties of location request

        locationRequest = new LocationRequest();

        //how often does the location check occur at default?
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        //most frequent update for location request
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // event that is triggered whenever update interval is met
        locationCallback =  new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //save the location
                //Location location = locationResult.getLastLocation();
                updateUIValues(locationResult.getLastLocation());

            }
        };

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()) {
                    //most accurate- use gps
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS Sensors");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Network for Location");
                }
            }
        });

        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_locationsupdates.isChecked()) {
                    //turn on location tracking
                    startLocationUpdates();
                } else {
                    //turn off location tracking
                    stopLocationUpdates();
                }
            }
        });


        addressString =  updateGPS();
        btn_bkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bkp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = "bzq";
                        int expenseOrIncome = 1;
                        int  imageId = 2;
                        String costClassification = "sex";
                        float money = -999;
                        String comments = "tongue by tongue therapy";
                        double lag = 1.0;
                        double lng = 1.0;
                        //if (username.equals("")) {
                          //  Toast.makeText(MainActivity.this, "Username can't be none!", Toast.LENGTH_SHORT).show();
                        //}
                        //else if (password.equals("")) {
                          //  Toast.makeText(MainActivity.this, "Password can't be none!", Toast.LENGTH_SHORT).show();
                        //}
                        //else {
                        sendRequestWithOkhttp(username, expenseOrIncome,imageId, addressString, "sex", money, comments,lag,lng);
//                        String username, int expenseOrIncome, int imageId,String address, String tag, float money, String comments, double lat, double lng
                        //sendRequestWithOkhttp(username, locationString, costClassification, money, comments);

                        //}
                    }
                });
            }
        });
    }


    private void stopLocationUpdates() {
        tv_updates.setText("Location is NOT being updated");
        tv_lat.setText("Location is NOT being updated");
        tv_lon.setText("Location is NOT being updated");
        tv_accuracy.setText("Location is NOT being updated");
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void startLocationUpdates() {
        tv_updates.setText("Location is being updated regularly");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        updateGPS();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                }
                else {
                    Toast.makeText(this,"The app requires your permission for location T_T", Toast.LENGTH_LONG).show();
                    finish(); //exit program, I THINK
                }
                break;
        }
    }

    private String updateGPS() {
        //Get permission to track GPS
        //Get current location from the fused client
        // update the UI / textView

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //User provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permission. put the values of location into the UI elements

                    //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //criteria = new Criteria();
                    //bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                    if(location!=null) {
                        addressString = updateUIValues(location);

                    }


                }
            });
        }
        else {
            // don't have permission yet

            //first check android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
        return addressString;

    }

    private String updateUIValues(Location location) {

        if(location!=null) {
            //update all text view objects with a new location
            //tv_lat.setText(String.valueOf(location.getLatitude()));
            tv_lat.setText(String.valueOf(location.getLatitude()));

            tv_lon.setText(String.valueOf(location.getLongitude()));
            tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        } else {
            tv_updates.setText("Null Location");
            tv_lat.setText("Null Location");
            tv_lon.setText("Null Location");
            tv_accuracy.setText("Null Location");


        }

        Geocoder geocoder = new Geocoder(Settings.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            //tv_address.setText(addresses.get(0).getAddressLine(0));
            tv_address.setText("City / 城市: " + addresses.get(0).getLocality());

            tv_lat.setText("State / 省: " + addresses.get(0).getAdminArea());
            tv_lon.setText("地址: " + addresses.get(0).getAddressLine(0));
            //addresses.get(0).getFeatureName() 国内是 比如深圳： 南山区/福田区等等
            addressString = addresses.get(0).getAddressLine(0);


        }
        catch (Exception e) {
            tv_address.setText("Unable to get street address");
        }

        return addressString;

    }

    private void sendRequestWithOkhttp(String username, int expenseOrIncome, int imageId,String address, String tag, float money, String comments, double lat, double lng){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    BookkeepingRequest bkpRequest = new BookkeepingRequest();
                    bkpRequest.setId(250);
                    bkpRequest.setEOI(expenseOrIncome);
                    bkpRequest.setImageID(imageId);
                    bkpRequest.setAddress(address);
                    bkpRequest.setTag(tag); //"AssWhopping"
                    bkpRequest.setExpenditure(money); //-500
                    bkpRequest.setComment(comments);
                    bkpRequest.setLat(lat);
                    bkpRequest.setLng(lng);
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("account", username)
//                            .add("nickname", nickname)
//                            .add("password", password)
//                            .add("email", email)
//                            .build();
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(parseRequestBody(bkpRequest)));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://139.180.180.99:8888/api/bookkeeping")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("printout",responseData);
                    if(parseJson(responseData)==200){
                        Looper.prepare();
                        Toast.makeText(Settings.this, "Success", Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();
                    }
                    else if (parseJson(responseData)==201){
                        Looper.prepare();
                        Toast.makeText(Settings.this, "Failed: The username existed", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        Looper.prepare();
                        Toast.makeText(Settings.this, "Failed: Unknown error, try again later", Toast.LENGTH_LONG).show();
                        finish();
                        Looper.loop();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int parseJson(String json) {
        Gson gson = new Gson();
        Status result = gson.fromJson(json, Status.class);
        return result.getStatus();
    }

    private String parseRequestBody(BookkeepingRequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }
//TODO: ADD BUTTONS FOR INPUTTING COST, CLASSIFICATION OF THE COST N A SUBMIT BUTTON
// THEN TEST SUBMITTING THAT SHIT




}
