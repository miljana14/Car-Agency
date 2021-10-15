package com.bootcamp.CarAgency.models.users;

public class LoginResponseModel {
    private boolean successful;
    private String info;

    public LoginResponseModel(boolean successful, String info) {
        this.successful = successful;
        this.info = info;
    }

    public LoginResponseModel(LoginResponseModel login) {
        this.successful = login.successful;
        this.info = login.info;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getInfo() {
        return info;
    }
}
