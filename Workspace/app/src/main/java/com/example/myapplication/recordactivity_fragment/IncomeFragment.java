package com.example.myapplication.recordactivity_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.database.ManageDatabase;
import com.example.myapplication.database.Record_TypeforEachOne;

import java.util.List;


public class IncomeFragment extends ParentFragment {


    @Override
    public void fillGridView() {
        super.fillGridView();
        List<Record_TypeforEachOne> outlist = ManageDatabase.getRecord_TypeforEachOneList(1);
        typelist.addAll(outlist);
        adapter.notifyDataSetChanged();
        textViewfortype.setText("Others");
        imageviewfortype.setImageResource(R.mipmap.in_others_fs);
    }

    @Override
    public void sentdatatoserver() {
        dataTypeForStore.setExpenseorincome(1);

    }
}