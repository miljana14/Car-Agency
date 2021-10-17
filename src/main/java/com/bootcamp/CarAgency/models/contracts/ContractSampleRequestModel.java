package com.bootcamp.CarAgency.models.contracts;

import java.util.Date;
import java.util.UUID;

public class ContractSampleRequestModel {
    private UUID user_id;
    private UUID car_id;
    private Date start_date;
    private Date end_date;

    public ContractSampleRequestModel(UUID user_id, UUID car_id, Date start_date, Date end_date) {
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
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
}
