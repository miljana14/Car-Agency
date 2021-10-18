package com.bootcamp.CarAgency.models.users;

public class LoginRequestModel {
    private String identification;
    private String password;

    public LoginRequestModel(String identification, String password) {
        this.identification = identification;
        this.password = password;
    }

    public LoginRequestModel(LoginRequestModel login) {
        this.identification = login.identification;
        this.password = login.password;
    }

    public String getIdentification() {
        return identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
