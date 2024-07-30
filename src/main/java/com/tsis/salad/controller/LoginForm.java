package com.tsis.salad.controller;

public class LoginForm {

    private String userid;
    private String pwd;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "userid='" + userid + '\'' +
                ",pwd='" + pwd + '\'' +
                '}';
    }
}
