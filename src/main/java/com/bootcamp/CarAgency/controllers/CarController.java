package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.CarDaoRequestSql;
import com.bootcamp.CarAgency.daos.CarDaoSql;
import com.bootcamp.CarAgency.daos.ContractDaoSql;
import com.bootcamp.CarAgency.daos.UserDaoSql;
import com.bootcamp.CarAgency.models.cars.CarModel;
import com.bootcamp.CarAgency.models.cars.CarRequestModel;
import com.bootcamp.CarAgency.models.cars.CarResponseModel;
import com.bootcamp.CarAgency.services.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class CarController {
    CarDaoSql cd = new CarDaoSql();
    UserDaoSql ud = new UserDaoSql();
    CarDaoRequestSql cdr = new CarDaoRequestSql();
    ContractDaoSql contractDao = new ContractDaoSql();
    UserService us = new UserService();

    @GetMapping("/cars")
    public List<CarModel> getAllCars(){
        return cd.getAllCars();
    }

    @GetMapping("/cars/{car_id}")
    public CarModel getCar(@PathVariable ("car_id") UUID id){
        return cd.getCar(id);
    }

    @PostMapping("/cars")
    public CarResponseModel addCar(@RequestBody CarModel car, @RequestHeader("idAdmin") UUID id){
        try{
            for (var y : cd.getAllCars()){
                if (!(y.getLicence_plate().equals(car.getLicence_plate()))) {
                    if (us.getAdmin(id)) {
                        cd.add(car);
                        return new CarResponseModel(id, "Successfully imported car!");
                    }
                }
                else {
                    return new CarResponseModel(id, "This car is already inserted");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new CarResponseModel(id,"User with this ID is not an admin!");
    }

    @DeleteMapping("/cars/{car_id}")
    public CarResponseModel delete(@PathVariable("car_id") UUID id, @RequestHeader("idAdmin") UUID idAdmin) {
        for (var y : cd.getAllCars()){
            if (us.getAdmin(idAdmin) && (y.getCar_id().equals(id))) {
                cd.delete(id);
                return new CarResponseModel(id, "Successfully deleted!");
            }
        }
        return new CarResponseModel(idAdmin,"User with this ID is not an admin or car is already deleted!");
}

    @PatchMapping("/cars/{car_id}")
    public CarResponseModel update(@RequestBody CarRequestModel car, @PathVariable("car_id") UUID id, @RequestHeader("idAdmin") UUID adminId){
        if (us.getAdmin(adminId)) {
            cdr.update(car,id);
            return new CarResponseModel(id, "Successfully updated");
        }
        return new CarResponseModel(adminId,"User with this ID is not an admin and don`t have permission to update!");
    }

    @GetMapping("/cars/search")
    public List<CarModel> searchCars(@RequestParam(required = false) Integer year,
                                                  @RequestParam(required = false) String make,
                                                  @RequestParam(required = false) String model,
                                                  @RequestParam(required = false) Boolean automatic,
                                                  @RequestParam(required = false) Double price,
                                                  @RequestParam(required = false) Integer power,
                                                  @RequestParam(required = false) Integer doors){
        return cdr.searchCars(year, make, model, automatic, price, power, doors);

    }

    @GetMapping("/cars/available")
    public List<CarModel> getAVailableCars(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        List<CarModel> cars = cd.getAllCars();

        return cd.getAvailableCars(startDate,endDate);
    }

    @GetMapping("/cars/available/search")
    public List<CarModel> searchAvailableSearchCars(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                              @RequestParam(required = false) Integer year,
                                              @RequestParam(required = false) String make,
                                              @RequestParam(required = false) String model,
                                              @RequestParam(required = false) Boolean automatic,
                                              @RequestParam(required = false) Double price,
                                              @RequestParam(required = false) Integer power,
                                              @RequestParam(required = false) Integer doors){

        return cdr.searchAvailableCars(startDate,endDate,year, make, model, automatic, price, power, doors);
    }

    @GetMapping("/cars/{car_id}/calendar")
    public List<Date> getAllCars(@PathVariable("car_id") UUID id){
        return contractDao.getAllUnaviableDate(id);
    }
}
