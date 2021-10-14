package com.bootcamp.CarAgency.models;

public class UserResponseModel {
    private boolean successful;
    private String info;

    public UserResponseModel(boolean successful, String info) {
        this.successful = successful;
        this.info = info;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getInfo() {
        return info;
    }
}
