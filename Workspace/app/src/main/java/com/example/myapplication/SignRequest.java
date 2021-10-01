package com.example.myapplication;

public class SignRequest {
    private String account;
    private String password;
    private String nickname;
    private String email;

    public void setAccount(String username) {
        this.account = username;
    }
    public void setpwd(String pwd) {
        this.password = pwd;
    }
    public void setnnm(String nnm) {
        this.nickname = nnm;
    }
    public void setemail(String email) {
        this.email = email;
    }
}
