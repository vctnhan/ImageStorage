package com.hanwool.imagestorage.model;

import java.io.Serializable;

public class ImageStorage implements Serializable {
    String path, date;

    public ImageStorage() {
    }

    public ImageStorage(String path, String date) {
        this.path = path;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
