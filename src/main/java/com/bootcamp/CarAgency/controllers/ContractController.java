package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.ContractDaoSql;
import com.bootcamp.CarAgency.daos.UserDaoSql;
import com.bootcamp.CarAgency.models.contracts.*;
import com.bootcamp.CarAgency.services.ContractService;
import com.bootcamp.CarAgency.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class ContractController {
    ContractDaoSql cds = new ContractDaoSql();
    UserDaoSql ud = new UserDaoSql();
    UserService us = new UserService();

    @GetMapping("/contracts")
    public List<ContractModel> getAllContracts(@RequestHeader("idAdmin") UUID id){
            if (us.getAdmin(id)){
                return cds.getAllContracts();
            }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/contracts/sample")
    public ContractSampleResponseModel contractSample(@RequestBody ContractSampleRequestModel sample){
        return new ContractSampleResponseModel(sample.getUser_id(),sample.getCar_id(),sample.getStart_date(),sample.getEnd_date(), ContractService.totalPrice(sample.getStart_date(),sample.getEnd_date(), cds.price(sample.getCar_id())),false);
    }

    @PostMapping("/contracts")
    public ContractsResponseModel signedContract(@RequestBody ContractSampleResponseModel sample){
        for (var x : ud.getAllUsers()){
            if (x.getUser_id().equals(sample.getUser_id())){
                cds.add(new ContractModel(UUID.randomUUID(),sample.getUser_id(),sample.getCar_id(),sample.getStart_date(),sample.getEnd_date(),ContractService.totalPrice(sample.getStart_date(),sample.getEnd_date(), cds.price(sample.getCar_id())),true,false));
                return new ContractsResponseModel(true,"Contract is created and it`s waiting for approval!");
            }
        }
        return new ContractsResponseModel(false,"Contract isn`t created!");
    }

    @GetMapping("/contracts/pending")
    public List<ContractModel> getAllUnapprovedContracts(@RequestHeader("idAdmin") UUID id){
        return cds.getAllUnapprovedContracts();
    }

    @GetMapping("/contracts/{user_id}/history")
    public List<ContractModel> getHistoryContracts(@PathVariable("user_id") UUID user_id){
        return cds.getHistoryContracts(user_id);
    }

    @PostMapping("/contracts/{contract_id}/approval")
    public boolean isContractApproved(@PathVariable("contract_id") UUID contract_id, @RequestHeader("idAdmin") UUID id, @RequestBody ContractApprovalRequestModel approved){
            if (us.getAdmin(id)){
                if (approved.isApproved()){
                    cds.update(approved,contract_id);
                    return true;
                }
                cds.delete(contract_id);
            }
        return false;
    }

}
