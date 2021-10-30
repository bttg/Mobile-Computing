package com.example.myapplication.recordactivity_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.BookkeepingRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MainPageForRecord;
import com.example.myapplication.R;
import com.example.myapplication.RecordActivity;
import com.example.myapplication.Settings;
import com.example.myapplication.Status;
import com.example.myapplication.database.ManageDatabase;
import com.example.myapplication.database.Record_TypeforEachOne;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ExpenditureFragment extends ParentFragment {



    @Override
    public void fillGridView() {
        super.fillGridView();
        List<Record_TypeforEachOne> outlist = ManageDatabase.getRecord_TypeforEachOneList(0);
        typelist.addAll(outlist);
        adapter.notifyDataSetChanged();
        textViewfortype.setText("Others");
        imageviewfortype.setImageResource(R.mipmap.ic_others_fs);
    }

    @Override
    public void sentdatatoserver() {
        dataTypeForStore.setExpenseorincome(0);
        MainPageForRecord mainPageForRecord = (MainPageForRecord) getActivity();
        String tempusername = mainPageForRecord.sendUsername();
        dataTypeForStore.setUsername(tempusername);

        //在这里把数据发到服务器
        String username = "bzq";
        int expenseOrIncome = 1;
        int  imageId = 2;
        String tag = "sex";
        float money = 999;
        String addressString = "laozhujia";
        String comments = "tongue by tongue therapy";
        double lag = 1.0;
        double lng = 1.0;
        sendRequestWithOkhttp(username, expenseOrIncome,imageId, addressString, tag, money, comments,lag,lng);

    }

    private void sendRequestWithOkhttp(String username, int expenseOrIncome, int imageId,String address, String tag, float money, String comments, double lat, double lng){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    BookkeepingRequest bkpRequest = new BookkeepingRequest();
                    bkpRequest.setId(250);
                    bkpRequest.setExpenseOrIncome(expenseOrIncome);
                    bkpRequest.setImageId(imageId);
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

                    RecordActivity recordActivity = (RecordActivity) getActivity();

                    if(parseJson(responseData)==200){
                        Looper.prepare();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                        Looper.loop();
                    }
                    else if (parseJson(responseData)==201){
                        Looper.prepare();
                        Toast.makeText(getActivity(), "Failed: The username existed", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "Failed: Unknown error, try again later", Toast.LENGTH_LONG).show();
                        getActivity().finish();
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



}