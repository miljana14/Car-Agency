package com.bootcamp.CarAgency.models.cars;

import java.util.UUID;

public class CarModel {
    private UUID car_id;
    private String licence_plate;
    private String make;
    private String model;
    private int year;
    private int engine_capacity;
    private String color;
    private double price;
    private int doors;
    private String size;
    private int power;
    private boolean automatic;
    private String fuel;
    private String image;

    public CarModel(UUID car_id, String licence_plate, String make, String model, int year, int engine_capacity, String color, double price, int doors, String size, int power, boolean automatic, String fuel, String image) {
        this.car_id = car_id;
        this.licence_plate = licence_plate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine_capacity = engine_capacity;
        this.color = color;
        this.price = price;
        this.doors = doors;
        this.size = size;
        this.power = power;
        this.automatic = automatic;
        this.fuel = fuel;
        this.image = image;
    }

    public CarModel(UUID car_id, String licence_plate, String make, String model, Integer year, Integer engine_capacity, String color, Double price, Integer doors, String size, Integer power, Boolean automatic, String fuel, String image) {
        this.car_id = car_id;
        this.licence_plate = licence_plate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine_capacity = engine_capacity;
        this.color = color;
        this.price = price;
        this.doors = doors;
        this.size = size;
        this.power = power;
        this.automatic = automatic;
        this.fuel = fuel;
        this.image = image;
    }

    public CarModel(CarModel carModel){
        this.car_id = carModel.car_id;
        this.licence_plate = carModel.licence_plate;
        this.make = carModel.make;
        this.model = carModel.model;
        this.year = carModel.year;
        this.engine_capacity = carModel.engine_capacity;
        this.color = carModel.color;
        this.price = carModel.price;
        this.doors = carModel.doors;
        this.size = carModel.size;
        this.power = carModel.power;
        this.automatic = carModel.automatic;
        this.fuel = carModel.fuel;
        this.image = carModel.image;
    }


    public UUID getCar_id() {
        return car_id;
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getEngine_capacity() {
        return engine_capacity;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getDoors() {
        return doors;
    }

    public String getSize() {
        return size;
    }

    public int getPower() {
        return power;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public String getFuel() {
        return fuel;
    }

    public String getImage() {
        return image;
    }

    public void setCar_id(UUID car_id) {
        this.car_id = car_id;
    }

    public void setLicence_plate(String licence_plate) {
        this.licence_plate = licence_plate;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setEngine_capacity(int engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
