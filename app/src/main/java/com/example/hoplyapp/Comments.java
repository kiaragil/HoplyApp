package com.example.hoplyapp;

public class Comments {
    private int id;
    private String user_id;
    private String context;
    private String stamp;

    public Comments(int id, String user_id, String context, String stamp) {
        this.id = id;
        this.user_id = user_id;
        this.context = context;
        this.stamp = stamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
