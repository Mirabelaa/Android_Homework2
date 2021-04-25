package com.example.homework2.models;

public class Album {

    private int userId;
    private int id;
    private String name;

    public Album(int userId, int id, String name) {
        this.userId = userId;
        this.id = id;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
