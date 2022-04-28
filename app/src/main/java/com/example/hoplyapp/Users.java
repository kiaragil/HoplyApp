package com.example.hoplyapp;

public class Users {
    private String id;
    private String name;
    private String stamp;

    public Users(String id, String name, String stamp) {
        this.id = id;
        this.name = name;
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
                ", stamp='" + stamp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
