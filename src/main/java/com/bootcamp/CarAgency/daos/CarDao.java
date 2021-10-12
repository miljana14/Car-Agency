package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.CarModel;
import com.bootcamp.CarAgency.models.CarRequestModel;

import java.util.List;
import java.util.UUID;

public interface CarDao {
    void add(CarModel cm);
    void delete(UUID id);
    CarModel getCar(UUID id);
    List<CarModel> getAllCars();
    //void update(CarRequestModel cm);
}
