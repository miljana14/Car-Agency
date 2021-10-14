package com.bootcamp.CarAgency.models;

public class UserRequestModel {

    private String username;
    private String password;
    private String newPassword;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String image;

    public UserRequestModel(String username, String password, String newPassword, String first_name, String last_name, String phone_number, String image) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getImage() {
        return image;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
