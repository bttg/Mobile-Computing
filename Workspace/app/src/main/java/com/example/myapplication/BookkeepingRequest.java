package com.example.myapplication;

//class for recording money spent or earned

public class BookkeepingRequest {
    private double expenditure;
    private String address;
    private String tag;
    private String comment;
    private int id;

    public void setId(int id) {
        this.id = 250;
    }
    public void setExpenditure(double expenditure) {
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



}
