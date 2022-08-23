package com.example.hoplyapp;

public class Reactions {
    private String user_id;
    private int post_id;
    private int type;

//    @Override
//    public String toString() {
//        return "Reactions{" +
//                "user_id='" + user_id + '\'' +
//                ", post_id=" + post_id +
//                ", type=" + type +
//                ", stamp='" + stamp + '\'' +
//                '}';
//    }

    private String stamp;

    @Override
    public String toString() {
        return user_id + " " + classify(type) + " - " + stamp;
    }

    public Reactions(String user_id, int post_id, int type, String stamp) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.type = type;
        this.stamp = stamp;
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

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String classify(int type) {
        if (type == 1) {
            return "liked this";
        }
        else if (type == 2) {
            return "disliked this";
        }
        else if (type == 3) {
            return "doesn't give a fuck about this";
        }
        else {
            return "";
        }
    }
}
