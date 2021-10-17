package com.bootcamp.CarAgency.models.contracts;

public class ContractsResponseModel {
    private boolean successful;
    private String info;

    public ContractsResponseModel(boolean successful, String info) {
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
