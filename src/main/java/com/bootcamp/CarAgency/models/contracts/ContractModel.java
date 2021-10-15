package com.bootcamp.CarAgency.models.contracts;

import java.util.Date;
import java.util.UUID;

public class ContractModel {
    private UUID contract_id;
    private UUID user_id;
    private UUID car_id;
    private Date start_date;
    private Date end_date;
    private double total_price;
    private boolean signed;
    private boolean approved;

    public ContractModel(UUID contract_id, UUID user_id, UUID car_id, Date start_date, Date end_date, double total_price, boolean signed, boolean approved) {
        this.contract_id = contract_id;
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_price = total_price;
        this.signed = signed;
        this.approved = approved;
    }

    public UUID getContract_id() {
        return contract_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public UUID getCar_id() {
        return car_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isApproved() {
        return approved;
    }
}

