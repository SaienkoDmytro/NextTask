package com.example.nexttask.recycler;

public class DataModel {
    private int image;
    private String status, temp;

    public DataModel(int image, String status, String temp) {
        this.image = image;
        this.status = status;
        this.temp = temp;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
