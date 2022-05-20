package com.example.hoplyapp;

import java.util.Calendar;
import java.util.Date;

public class Users {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Date stamp;

    public Users(Integer id, String name, String username, String password, Date stamp) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.stamp = stamp;
    }

    public Users() {
    }

    //toString


    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", stamp='" + stamp + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
