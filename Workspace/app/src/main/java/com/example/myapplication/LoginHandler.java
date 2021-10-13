package com.example.myapplication;

public class LoginHandler extends Status{
    private int id=0;
    private String nickname="";
    private String email="";

    public String getNickname() {
        return nickname;
    }
    public String getEmail() {
        return email;
    }
    public int getStatus() {
        return super.getStatus();
    }
    public int getID() {return id;}

}
