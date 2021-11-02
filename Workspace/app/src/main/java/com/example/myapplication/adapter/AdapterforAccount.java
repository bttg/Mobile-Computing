package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DataHandler;
import com.example.myapplication.R;
import com.example.myapplication.database.DataTypeForShow;
import com.example.myapplication.database.DataTypeForStore;
import com.example.myapplication.database.Record_TypeforEachOne;

import java.util.List;

public class AdapterforAccount extends BaseAdapter {
    Context context;
    List<DataHandler.Data> mDatas;
    LayoutInflater inflater;

    public AdapterforAccount(Context context, List<DataHandler.Data> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = inflater.inflate(R.layout.item_main_listview,viewGroup,false);
            holder =  new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        DataHandler.Data dataTypeForShow = mDatas.get(i);
        holder.typeImageview.setImageResource(dataTypeForShow.getImageID());
        holder.typeTextview.setText(dataTypeForShow.getTag());
        holder.commentTextview.setText(dataTypeForShow.getComment());
        holder.timeTextview.setText(dataTypeForShow.getDate());
        holder.moneyTextview.setText("$ "+String.valueOf(dataTypeForShow.getMoney()));
        return view;
    }

    class ViewHolder{
        ImageView typeImageview;
        TextView typeTextview;
        TextView commentTextview;
        TextView timeTextview;
        TextView moneyTextview;
        public ViewHolder(View view){
            typeImageview = view.findViewById(R.id.item_main_listview_imageview);
            typeTextview = view.findViewById(R.id.item_main_listview_title);
            commentTextview = view.findViewById(R.id.item_main_listview_comment);
            timeTextview = view.findViewById(R.id.item_main_listview_time);
            moneyTextview = view.findViewById(R.id.item_main_listview_money);
        }
    }



}
