package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.CarModel;
import com.bootcamp.CarAgency.models.CarRequestModel;

import java.util.List;
import java.util.UUID;

public interface CarDaoRequest {
    List<CarRequestModel> getAllCars();
    void update(CarRequestModel cm, UUID id);
    List<CarModel> searchCars(Integer year, String make, String model, Boolean automatic, Double price, Integer power, Integer doors);
}
