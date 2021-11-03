package com.example.myapplication;

public class globalVariable {
    String fixedID;
    public globalVariable(){}
    public globalVariable(String fixedID) {
        this.fixedID = fixedID;
    }



    public String getFixedID() {
        return fixedID;
    }

    public void setFixedID(String fixedID) {
        this.fixedID = fixedID;
    }
}
