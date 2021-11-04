package com.example.myapplication.recordactivity_fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.KeyboardView;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Settings;
import com.example.myapplication.database.DataTypeForStore;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.example.myapplication.util.Utils_Comment;
import com.example.myapplication.util.Utils_KeyboardView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ParentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//Expense module in the record page
public abstract class ParentFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    KeyboardView customkeyboardview;
    EditText edittextformoney;
    ImageView imageviewfortype;
    TextView textViewfortype;
    TextView textViewforcomment;
    TextView textViewfortime;
    GridView gridViewforttype;
    List<Record_TypeforEachOne> typelist;
    AdapterForRecordType adapter;
    DataTypeForStore dataTypeForStore;//Save the data that needs to be inserted into the database in object format

    private static final int PERMISSIONS_FINE_LOCATION = 99; //arbitrary choice

    protected String addressString;
    //variable to remember if we are tracking location or nah
    boolean updateOn = false;

    //Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;

    //Location Request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallback;


    //
//    public ParentFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ParentFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ParentFragment newInstance(String param1, String param2) {
//        ParentFragment fragment = new ParentFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility((Activity) getActivity(), SpeechConstant.APPID +"=b8e23876");
        dataTypeForStore = new DataTypeForStore();
        dataTypeForStore.setImage_name("Others");
        dataTypeForStore.setId_for_selected(R.mipmap.ic_others_fs);
        ImageButton record = (ImageButton) getActivity().findViewById(R.id.recordactivity_fragment_relativelayout_microphone);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRecording();
            }
        });


        //set all properties of location request
        locationRequest = new LocationRequest();

        //how often does the location check occur at default?
        locationRequest.setInterval(1000 * 30);

        //most frequent update for location request
        locationRequest.setFastestInterval(1000 * 5);

        //Only use approximate locations
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // event that is triggered whenever update interval is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //save the location
                Location location = locationResult.getLastLocation();
                updateUIValues(locationResult.getLastLocation());


            }
        };

        addressString = updateGPS();



    } //end of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initialView(view);
        initialTime();
        //Fill the GridView with data
        fillGridView();
        //Set the click event of each item in the GridView
        setGridViewPressOnClickListener();
        return view;
    }

    //Get the current time and display it on the interface
    private void initialTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String time = simpleDateFormat.format(date);
        textViewfortime.setText(time);
        dataTypeForStore.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dataTypeForStore.setYear(year);
        dataTypeForStore.setMonth(month);
        dataTypeForStore.setDay(day);
    }
    class MyRecoListener implements RecognizerListener {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result_string = recognizerResult.getResultString();
            textViewforcomment.append(result_string);
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

    }


    private void setGridViewPressOnClickListener() {
        gridViewforttype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.ifSelectedPosition = i;
                adapter.notifyDataSetInvalidated();//Prompt that the drawing has changed
                Record_TypeforEachOne record_typeforEachOne = typelist.get(i);
                String image_name = record_typeforEachOne.getImage_name();
                dataTypeForStore.setImage_name(image_name);
                textViewfortype.setText(image_name);
                int id_for_selected = record_typeforEachOne.getId_for_selected();
                imageviewfortype.setImageResource(id_for_selected);
                dataTypeForStore.setId_for_selected(id_for_selected);
            }
        });
    }

    public void fillGridView() {
        typelist = new ArrayList<>();
        adapter = new AdapterForRecordType(getContext(), typelist);
        gridViewforttype.setAdapter(adapter);

    }

    private void initialView(View view) {
        customkeyboardview = view.findViewById(R.id.recordactivity_fragment_relativelayout_keyboard);
        edittextformoney = view.findViewById(R.id.recordactivity_fragment_relativelayout_money);
        imageviewfortype = view.findViewById(R.id.recordactivity_fragment_relativelayout_topimage);
        textViewfortype = view.findViewById(R.id.recordactivity_fragment_relativelayout_typetext);
        textViewforcomment = view.findViewById(R.id.recordactivity_fragment_relativelayout_comment);
        textViewfortime = view.findViewById(R.id.recordactivity_fragment_relativelayout_time);
        gridViewforttype = view.findViewById(R.id.recordactivity_fragment_relativelayout_gridview);
        textViewforcomment.setOnClickListener(this);
        textViewfortime.setOnClickListener(this);

        //Let the custom keyboard show up
        Utils_KeyboardView keyboradutils = new Utils_KeyboardView(customkeyboardview, edittextformoney);
        keyboradutils.make_keyboard_visible();
        //Set up the interface, listen to the confirmation button is clicked
        keyboradutils.setPressOKListener(new Utils_KeyboardView.PressOKListener() {
            @Override
            public void PressOK() {
                //Get the amount of input money
                String moneystring = edittextformoney.getText().toString();
                if (moneystring.equals("0") || TextUtils.isEmpty(moneystring)) {
                    getActivity().finish();
                    return;
                }
                float moneyfloat = Float.parseFloat(moneystring);
                dataTypeForStore.setMoneyaccount(moneyfloat);
                //
                sentdatatoserver();
                //
                getActivity().finish();
            }
        });

    }

    public abstract void sentdatatoserver();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recordactivity_fragment_relativelayout_time:

                break;
            case R.id.recordactivity_fragment_relativelayout_comment:
                makedialogvisible();

            case R.id.recordactivity_fragment_relativelayout_microphone:
                //TODO recording


        }
    }

    //GPS Stuff
    private void getGpsAddress() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

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
                    Toast.makeText(getActivity(),"The app requires your permission for location T_T", Toast.LENGTH_LONG).show();
                    getActivity().finish(); //exit program, I THINK
                }
                break;
        }
    }

    private String updateGPS() {
        //Get permission to track GPS
        //Get current location from the fused client
        // update the UI / textView

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //User provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permission. put the values of location into the UI elements

                    //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //criteria = new Criteria();
                    //bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                    if(location!=null) {
                        addressString = updateUIValues(location);

                    }else {
                        Toast.makeText(getActivity(),"Null location, wait a bit more!", Toast.LENGTH_LONG).show();
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
            //tv_lat.setText(String.valueOf(location.getLatitude()));
            //tv_lon.setText(String.valueOf(location.getLongitude()));
            //tv_accuracy.setText(String.valueOf(location.getAccuracy()));
            Toast.makeText(getActivity(),String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                //tv_address.setText(addresses.get(0).getAddressLine(0));
                //tv_address.setText("City / 城市: " + addresses.get(0).getLocality());

                //tv_lat.setText("State / 省: " + addresses.get(0).getAdminArea());
                //tv_lon.setText("地址: " + addresses.get(0).getAddressLine(0));
                //addresses.get(0).getFeatureName() 国内是 比如深圳： 南山区/福田区等等
                addressString = addresses.get(0).getAddressLine(0);
                dataTypeForStore.setGpsaddress(addressString);
                dataTypeForStore.setLat(location.getLatitude());
                dataTypeForStore.setLng(location.getLongitude());
                Toast.makeText(getActivity(),addressString, Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Toast.makeText(getActivity(),"Unable to get street address, check GPS/ WiFi/ Cellular", Toast.LENGTH_LONG).show();
                //tv_address.setText("Unable to get street address");
            }

        } else {
            //tv_updates.setText("Null Location");
            //tv_lat.setText("Null Location");
            //tv_lon.setText("Null Location");
            //tv_accuracy.setText("Null Location");
            Toast.makeText(getActivity(),"Null location, wait a bit more!", Toast.LENGTH_LONG).show();
        }
        return addressString;

    }

    public void makedialogvisible() {
        final Utils_Comment utils_comment = new Utils_Comment(getContext());
        utils_comment.show();
//        utils_comment.changeDialogSize();
        handler.sendEmptyMessageDelayed(1,100);
        utils_comment.setPressEnsureListener(new Utils_Comment.PressEnsureListener() {
            @Override
            public void PressEnsure() {
                String message = utils_comment.getComment();
                if (!TextUtils.isEmpty(message)){
                    textViewforcomment.setText(message);
                    dataTypeForStore.setComment(message);
                }
                utils_comment.cancel();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    public void StartRecording(){
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer((Activity)getActivity(), null);
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        mIat.setParameter(SpeechConstant.RESULT_TYPE,"plain");
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");
        mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        mIat.setParameter(SpeechConstant.VAD_BOS,"4000");
        mIat.setParameter(SpeechConstant.VAD_EOS,"1000");
        mIat.setParameter(SpeechConstant.ASR_PTT,"0");
        mIat.startListening(new MyRecoListener());
    }





}