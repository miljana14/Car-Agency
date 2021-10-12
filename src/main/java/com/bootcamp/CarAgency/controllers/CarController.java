package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.CarDaoRequestSql;
import com.bootcamp.CarAgency.daos.CarDaoSql;
import com.bootcamp.CarAgency.daos.UserDaoSql;
import com.bootcamp.CarAgency.models.CarModel;
import com.bootcamp.CarAgency.models.CarRequestModel;
import com.bootcamp.CarAgency.models.CarResponseModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CarController {
    CarDaoSql cd = new CarDaoSql();
    UserDaoSql ud = new UserDaoSql();
    CarDaoRequestSql cdr = new CarDaoRequestSql();

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
            for (var x : ud.getAllUsers()) {
                for (var y : cd.getAllCars()){
                    if (!(y.getLicence_plate().equals(car.getLicence_plate()))) {
                        if (x.isAdmin() && x.getUser_id().toString().equals(id.toString())) {
                            cd.add(car);
                            return new CarResponseModel(id, "Successfully imported car!");
                        }
                    }
                    else {
                        return new CarResponseModel(id, "This car is already inserted");
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new CarResponseModel(id,"User with this ID is not an admin!");
    }

    @DeleteMapping("/cars/{car_id}")
    public CarResponseModel delete(@PathVariable("car_id") UUID id, @RequestHeader("idAdmin") UUID adminId) {
        for (var x : ud.getAllUsers()) {
            for (var y : cd.getAllCars()){
                if (x.getUser_id().toString().equals(adminId.toString()) && x.isAdmin() && (y.getCar_id().equals(id))) {
                    cd.delete(id);
                    return new CarResponseModel(id, "Successfully deleted!");
                }

            }

        }
        return new CarResponseModel(adminId,"User with this ID is not an admin or car is already deleted!");
}

    @PatchMapping("/cars/{car_id}")
    public CarResponseModel update(@RequestBody CarRequestModel car, @PathVariable("car_id") UUID id, @RequestHeader("idAdmin") UUID adminId){
            for (var x : ud.getAllUsers()) {
                if (x.getUser_id().toString().equals(adminId.toString()) && x.isAdmin()) {
                    cdr.update(car,id);
                    return new CarResponseModel(id, "Successfully updated");
                }

            }

            return new CarResponseModel(adminId,"User with this ID is not an admin and don`t have permission to update!");
    }
}
