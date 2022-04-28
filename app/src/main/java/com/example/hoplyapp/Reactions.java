package com.example.hoplyapp;

public class Reactions {
    private String user_id;
    private int post_id;
    private int type;
    private String timestamp;

    public Reactions(String user_id, int post_id, int type, String timestamp) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
