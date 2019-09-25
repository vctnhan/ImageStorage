package com.hanwool.imagestorage.Model;

import java.io.Serializable;

public class PathAndName implements Serializable {
    String path, name;

    public PathAndName() {
    }

    public PathAndName(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
