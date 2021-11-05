package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker markerTestPosition;
    private List<DataHandler.Data> Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Data = (List<DataHandler.Data>) getIntent().getSerializableExtra("Data");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        Log.d("checkPoint","point 12");

        for(int i=0; i<Data.size(); i++)
        {

            Log.d("Tag",Data.get(i).getTag());
            Log.d("Lat",Data.get(i).getLat());
            Log.d("Lng",Data.get(i).getLng());

            String tag = Data.get(i).getTag();
            Double money = Data.get(i).getMoney();
         //   String date = Data.get(i).getDate();
            Double latitude = Double.parseDouble(Data.get(i).getLat());
            Double longitude = Double.parseDouble(Data.get(i).getLng());

            if(latitude!=-1000)
            {
                LatLng position = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(position).title(tag + ": $" + money));
             //   newPlace.setSnippet("Test");

            }

        }

        // Add a marker in Sydney and move the camera

//        LatLng[] pList = new LatLng[5];
//        pList[0] = new LatLng(-34, 151);
//        pList[1] = new LatLng(-33, 150);
//        pList[2] = new LatLng(-32, 149);
//        pList[3] = new LatLng(-31, 148);
//        pList[4] = new LatLng(-30, 147);
//
//        for(int i=0; i<5; i++)
//        {
//            mMap.addMarker(new MarkerOptions().position(pList[i]).title("Marker in Sydney"));
//        }
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng position = new LatLng(Double.parseDouble(Data.get(Data.size()-1).getLat()), Double.parseDouble(Data.get(Data.size()-1).getLng()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
}