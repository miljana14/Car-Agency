package com.bootcamp.CarAgency.models.users;

import java.util.UUID;

public class RegisterRequestModel {
    private UUID id;
    private String username;
    private String email;
    private String password;

    public RegisterRequestModel(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
