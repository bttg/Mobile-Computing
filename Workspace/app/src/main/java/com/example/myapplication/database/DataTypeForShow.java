package com.example.myapplication.database;

public class DataTypeForShow {
    double money;
    String tag;
    String date;
    String comment;
    int imageID;
    String lat;
    String lng;

    public DataTypeForShow() {
    }

    public DataTypeForShow(double money, String tag, String date, String comment, int imageID, String lat, String lng) {
        this.money = money;
        this.tag = tag;
        this.date = date;
        this.comment = comment;
        this.imageID = imageID;
        this.lat = lat;
        this.lng = lng;
    }

    public double getMoney() {
        return money;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getImageID() {
        return imageID;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
