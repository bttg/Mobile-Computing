package com.example.myapplication.recordactivity_fragment;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.DataTypeForStore;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.example.myapplication.util.Utils_Comment;
import com.example.myapplication.util.Utils_KeyboardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ParentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//Expense module in the record page
public abstract class ParentFragment extends Fragment implements View.OnClickListener{

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
        dataTypeForStore = new DataTypeForStore();
        dataTypeForStore.setImage_name("Others");
        dataTypeForStore.setId_for_selected(R.mipmap.ic_others_fs);
    }

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
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dataTypeForStore.setYear(year);
        dataTypeForStore.setMonth(month);
        dataTypeForStore.setDay(day);
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
        Utils_KeyboardView keyboradutils = new Utils_KeyboardView(customkeyboardview,edittextformoney);
        keyboradutils.make_keyboard_visible();
        //Set up the interface, listen to the confirmation button is clicked
        keyboradutils.setPressOKListener(new Utils_KeyboardView.PressOKListener() {
            @Override
            public void PressOK() {
                //Get the amount of input money
                String moneystring = edittextformoney.getText().toString();
                if (moneystring.equals("0") || TextUtils.isEmpty(moneystring)){
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
        switch (view.getId()){
            case R.id.recordactivity_fragment_relativelayout_time:

                break;
            case R.id.recordactivity_fragment_relativelayout_comment:
                makedialogvisible();

            case R.id.recordactivity_fragment_relativelayout_maplabel:
                getGpsAddress();

            case R.id.recordactivity_fragment_relativelayout_microphone:

                break;
        }
    }

    private void getGpsAddress() {


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



}