package com.example.myapplication.recordactivity_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.Record_TypeforEachOne;

import java.util.List;

public class AdapterForRecordType extends BaseAdapter {

    Context context;
    List<Record_TypeforEachOne> typeList;
    int ifSelectedPosition = 0;

    public AdapterForRecordType(Context context, List<Record_TypeforEachOne> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int i) {
        return typeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.expenditure_fragment_gridview,viewGroup,false);
        //Find the controls in the layout
        ImageView imageView = view.findViewById(R.id.recordacctivity_fragment_item_imageview);
        TextView textView = view.findViewById(R.id.recordactivity_fragment_item_textview);
        //Get the data source at the specified location
        Record_TypeforEachOne record_typeforEachOne = typeList.get(i);
        textView.setText(record_typeforEachOne.getImage_name());
        //Determine whether the location is selected
        if(ifSelectedPosition == i){
            imageView.setImageResource(record_typeforEachOne.getId_for_selected());
        }else{
            imageView.setImageResource(record_typeforEachOne.getId_for_notselected());
        }


        return view;
    }

}
