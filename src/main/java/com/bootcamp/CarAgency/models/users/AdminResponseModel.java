package com.bootcamp.CarAgency.models.users;

public class AdminResponseModel {
    private boolean successful;
    private String message;

    public AdminResponseModel(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
