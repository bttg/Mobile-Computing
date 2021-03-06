package com.example.myapplication.database;

public class DataTypeForStore {
    int identity;
    String image_name;
    int id_for_selected;
    String comment;
    float moneyaccount;
    String time;
    int year;
    int month;
    int day;
    int expenseorincome;
    String gpsaddress;
    String username;
    double lat;
    double lng;

    public DataTypeForStore() {
    }

    public DataTypeForStore(int identity, String image_name, int id_for_selected, String comment, float moneyaccount, String time, int year, int month, int day, int expenseorincome, String gpsaddress, String username, double lat, double lng) {
        this.identity = identity;
        this.image_name = image_name;
        this.id_for_selected = id_for_selected;
        this.comment = comment;
        this.moneyaccount = moneyaccount;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.expenseorincome = expenseorincome;
        this.gpsaddress = gpsaddress;
        this.username = username;
        this.lat = lat;
        this.lng = lng;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getId_for_selected() {
        return id_for_selected;
    }

    public void setId_for_selected(int id_for_selected) {
        this.id_for_selected = id_for_selected;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getMoneyaccount() {
        return moneyaccount;
    }

    public void setMoneyaccount(float moneyaccount) {
        this.moneyaccount = moneyaccount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getExpenseorincome() {
        return expenseorincome;
    }

    public void setExpenseorincome(int expenseorincome) {
        this.expenseorincome = expenseorincome;
    }

    public String getGpsaddress() {
        return gpsaddress;
    }

    public void setGpsaddress(String gpsaddress) {
        this.gpsaddress = gpsaddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getLat() { return lat; }

    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }

    public void setLng(double lng) { this.lng = lng; }
}
