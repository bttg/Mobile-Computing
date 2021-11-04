package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class DataHandler implements Serializable{
    private int code;
    private double income;
    private double expenditure;
    private List<Data> data;

    public DataHandler(int code, double income, double expenditure, List<Data> data) {
        this.code = code;
        this.income = income;
        this.expenditure = expenditure;
        this.data = data;
    }

    public class Data implements Serializable{
        private double money;
        private String tag;
        private String date;
        private String comment;
        private int imageID;
        private String lat;
        private String lng;

        public String getDate() {
            return date;
        }

        public String getComment() {
            return comment;
        }

        public int getImageID() {
            return imageID;
        }

        public double getMoney() {
            return money;
        }

        public String getTag() {
            return tag;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }

    public int getCode() {
        return code;
    }

    public List<Data> getData() {
        return this.data;
    }

    public double getIncome() {
        return income;
    }

    public double getExpenditure() {
        return expenditure;
    }



}
