package com.example.myapplication.recordactivity_fragment;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.BookkeepingRequest;
import com.example.myapplication.R;
import com.example.myapplication.RecordActivity;
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
//        dataTypeForStore.setExpenseorincome(1);
        dataTypeForStore.setExpenseorincome(1);

        RecordActivity mainPageForRecord = (RecordActivity) getActivity();
        String tempusername = mainPageForRecord.sendUsername();
        dataTypeForStore.setUsername(tempusername);

        //在这里把数据发到服务器
        String username = dataTypeForStore.getUsername();
        int expenseOrIncome = dataTypeForStore.getExpenseorincome();
        int  imageId = dataTypeForStore.getId_for_selected();
        String tag = dataTypeForStore.getImage_name();
        float money = dataTypeForStore.getMoneyaccount();
        String addressString = "no Address";
        String comments;
        if (dataTypeForStore.getComment() == null){
            comments = "";
        }else{
            comments = dataTypeForStore.getComment();
        }
        double lat = -1000;
        double lng = -1000;
        sendRequestWithOkhttp(username, expenseOrIncome,imageId, addressString, tag, money, comments,lat,lng);
    }

    private void sendRequestWithOkhttp(String username, int expenseOrIncome, int imageId,String address, String tag, float money, String comments, double lat, double lng){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                try {
                    BookkeepingRequest bkpRequest = new BookkeepingRequest();
                    bkpRequest.setId(Integer.valueOf(username));
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