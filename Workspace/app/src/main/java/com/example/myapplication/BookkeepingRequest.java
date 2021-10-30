package com.example.myapplication;

//class for recording money spent or earned

public class BookkeepingRequest {
    private int id;
    private float expenditure;
    private int expenseOrIncome;
    private int imageId;
    private String address;
    private String tag;
    private String comment;
    private double lat;
    private double lng;


    public void setId(int id) {
        this.id = 250;
    }
    public void setExpenditure(float expenditure) {
        this.expenditure = expenditure;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setExpenseOrIncome(int expenseOrIncome) { this.expenseOrIncome = expenseOrIncome; }
    public void setImageId(int imageId) { this.imageId = imageId; }
    public void setLat(double lat) { this.lat = lat; }
    public void setLng(double lng) { this.lng = lng; }

}
