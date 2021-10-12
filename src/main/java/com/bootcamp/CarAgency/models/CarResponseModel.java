package com.bootcamp.CarAgency.models;

import java.util.UUID;

public class CarResponseModel {
    private UUID userId;
    private String message;

    public CarResponseModel(UUID userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
