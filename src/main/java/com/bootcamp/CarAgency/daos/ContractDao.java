package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.ContractModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ContractDao {
    List<ContractModel> getAllContracts();
    //List<Date> getAllUnaviableDate(UUID carId);
}
