package com.example.myapplication.recordactivity_fragment;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.ManageDatabase;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.example.myapplication.util.Utils_KeyboardView;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ExpenditureFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//Expense module in the record page
public class ExpenditureFragment extends Fragment {

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
    private AdapterForRecordType adapter;

//
//    public ExpenditureFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ExpenditureFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ExpenditureFragment newInstance(String param1, String param2) {
//        ExpenditureFragment fragment = new ExpenditureFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initialView(view);
        //Fill the GridView with data
        fillGridView();
        //Set the click event of each item in the GridView
        setGridViewPressOnClickListener();
        return view;
    }

    private void setGridViewPressOnClickListener() {
        gridViewforttype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.ifSelectedPosition = i;
                adapter.notifyDataSetInvalidated();

                Record_TypeforEachOne record_typeforEachOne = typelist.get(i);
                String image_name = record_typeforEachOne.getImage_name();
                textViewfortype.setText(image_name);
                int id_for_selected = record_typeforEachOne.getId_for_selected();
                imageviewfortype.setImageResource(id_for_selected);
            }
        });
    }

    private void fillGridView() {
        typelist = new ArrayList<>();
        adapter = new AdapterForRecordType(getContext(), typelist);
        gridViewforttype.setAdapter(adapter);
        //获取数据库中的数据源
        List<Record_TypeforEachOne> outlist = ManageDatabase.getRecord_TypeforEachOneList(0);
        typelist.addAll(outlist);
        adapter.notifyDataSetChanged();
    }

    private void initialView(View view) {
        customkeyboardview = view.findViewById(R.id.recordactivity_fragment_relativelayout_keyboard);
        edittextformoney = view.findViewById(R.id.recordactivity_fragment_relativelayout_money);
        imageviewfortype = view.findViewById(R.id.recordactivity_fragment_relativelayout_topimage);
        textViewfortype = view.findViewById(R.id.recordactivity_fragment_relativelayout_typetext);
        textViewforcomment = view.findViewById(R.id.recordactivity_fragment_relativelayout_comment);
        textViewfortime = view.findViewById(R.id.recordactivity_fragment_relativelayout_time);
        gridViewforttype = view.findViewById(R.id.recordactivity_fragment_relativelayout_gridview);

        //Let the custom keyboard show up
        Utils_KeyboardView keyboradutils = new Utils_KeyboardView(customkeyboardview,edittextformoney);
        keyboradutils.make_keyboard_visible();
        //Set up the interface, listen to the confirmation button is clicked
        keyboradutils.setPressOKListener(new Utils_KeyboardView.PressOKListener() {
            @Override
            public void PressOK() {
                //
                //
                //
            }
        });

    }



}