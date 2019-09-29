package com.hanwool.imagestorage.Model;

public class Data {
    public int imageId;
    public String txt;

    public Data(int imageId, String text) {

        this.imageId = imageId;
        this.txt=text;
    }

    public Data() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
