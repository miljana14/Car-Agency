package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.cars.CarModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CarDao {
    void add(CarModel cm);
    void delete(UUID id);
    CarModel getCar(UUID id);
    List<CarModel> getAllCars();
    List<CarModel> getAvailableCars(Date startDate, Date endDate);
}
