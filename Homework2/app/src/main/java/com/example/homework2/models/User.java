package com.example.homework2.models;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private boolean arePostsVisible;
    private ArrayList<Post> posts;

    public User(String name,int id){
        this.name=name;
        this.id=id;
        posts=new ArrayList<>();
        this.arePostsVisible=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean arePostsVisible() {
        return arePostsVisible;
    }

    public void setArePostsVisible(boolean arePostsVisible) {
        this.arePostsVisible = arePostsVisible;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
