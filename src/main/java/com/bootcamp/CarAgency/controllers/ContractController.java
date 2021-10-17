package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.ContractDaoSql;
import com.bootcamp.CarAgency.daos.UserDaoSql;
import com.bootcamp.CarAgency.models.contracts.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class ContractController {
    ContractDaoSql cds = new ContractDaoSql();
    UserDaoSql ud = new UserDaoSql();

    public double totalPrice(Date start_date, Date end_date, double price){
        Long days = end_date.getTime() - start_date.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(days, TimeUnit.MILLISECONDS);
        return price * diffrence;
    }

    @GetMapping("/contracts")
    public List<ContractModel> getAllContracts(@RequestHeader("idAdmin") UUID id){
        for (var x : ud.getAllUsers()){
            if (x.isAdmin() && x.getUser_id().equals(id)){
                return cds.getAllContracts();
            }
        }
        return null;
    }

    @PostMapping("/contracts/sample")
    public ContractSampleResponseModel contractSample(@RequestBody ContractSampleRequestModel sample){
        return new ContractSampleResponseModel(sample.getUser_id(),sample.getCar_id(),sample.getStart_date(),sample.getEnd_date(), totalPrice(sample.getStart_date(),sample.getEnd_date(), cds.price(sample.getCar_id())),false);
    }

    @PostMapping("/contracts")
    public ContractsResponseModel signedContract(@RequestBody ContractSampleResponseModel sample){
        for (var x : ud.getAllUsers()){
            if (x.getUser_id().equals(sample.getUser_id())){
                cds.add(new ContractModel(UUID.randomUUID(),sample.getUser_id(),sample.getCar_id(),sample.getStart_date(),sample.getEnd_date(),totalPrice(sample.getStart_date(),sample.getEnd_date(), cds.price(sample.getCar_id())),true,false));
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
//        for (var x : cds.getAllUnapprovedContracts()){
//            if (x.getContract_id().equals(contract_id) && approved.isApproved()){
//                x.setApproved(true);
//                return true;
//        }}
        for (var x : ud.getAllUsers()){
            if (x.isAdmin() && x.getUser_id().equals(id)){
                if (approved.isApproved()){
                    cds.update(approved,contract_id);
                    return true;
                }
                cds.delete(contract_id);
            }
        }
        return false;
    }

}
