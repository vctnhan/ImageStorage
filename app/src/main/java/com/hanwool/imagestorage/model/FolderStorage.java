package com.hanwool.imagestorage.model;

import java.io.Serializable;

public class FolderStorage implements Serializable {
    String path;

    public FolderStorage() {
    }

    public FolderStorage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
