package com.example.hoplyapp;

import java.util.Calendar;
import java.util.Date;

public class Users {
    private String username;
    private String name;
    private String password;
    private String repassword;
    private String stamp;

    public Users(String username, String name, String password, String repassword,String stamp) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.repassword = repassword;
        this.stamp = stamp;
    }

    public Users(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = "password123";
        this.repassword = "password123";
        this.stamp = stamp;
    }

    public Users() { }

    //toString
    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + repassword + '\'' +
                ", stamp='" + stamp + '\'' +
                '}';
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() { return repassword; }

    public void setRepassword(String repassword) { this.repassword = repassword; }

    public String getStamp() { return stamp; }

    public void setStamp(String stamp) { this.stamp = stamp; }
}
