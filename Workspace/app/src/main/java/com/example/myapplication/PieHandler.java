package com.example.myapplication;
import java.util.List;

public class PieHandler {
    private int code;
    private List<Data> data;


    public class Data {
        private String money;
        private String tag;

        public String getMoney() {
            return money;
        }

        public String getTag() {
            return tag;
        }
    }

    public int getCode() {
        return code;
    }

    public List<Data> getData() {
        return data;
    }
}
