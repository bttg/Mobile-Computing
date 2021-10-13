package com.example.myapplication;

public class LoginHandler extends Status{
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
}
