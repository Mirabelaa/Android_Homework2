package com.example.homework2.models;

public class Picture {
    private int albumId;
    private int id;
    private String url;

    public Picture(int albumId, int id, String url) {
        this.albumId = albumId;
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
