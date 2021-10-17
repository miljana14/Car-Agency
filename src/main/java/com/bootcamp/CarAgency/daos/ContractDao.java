package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.contracts.ContractModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ContractDao {
    List<Date> getAllUnaviableDate(UUID carId);
    List<ContractModel> getAllContracts();
    List<ContractModel> getAllUnapprovedContracts();
    List<ContractModel> getHistoryContracts(UUID userId);
    ContractModel getContract(UUID id);
    void add(ContractModel cm);
}
