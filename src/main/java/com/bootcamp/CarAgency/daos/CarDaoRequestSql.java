package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.cars.CarModel;
import com.bootcamp.CarAgency.models.cars.CarRequestModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.UUID;

public class CarDaoRequestSql implements CarDaoRequest{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public List<CarRequestModel> getAllCars() {
        List<CarRequestModel> allCars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT licence_plate, make, model, year , engine_capacity , color , price, doors, " +
                                                "size , power, automatic , fuel, image FROM cars");
            while(rs.next()){
                CarRequestModel newCar = new CarRequestModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getBoolean(11),
                        rs.getString(12),
                        rs.getString(13)
                );

                allCars.add(newCar);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCars;
    }

    @Override
    public void update(CarRequestModel cm, UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE cars " +
                    "SET licence_plate = ?, " +
                    "make = ?, " +
                    "model = ?, " +
                    "year = ?, " +
                    "engine_capacity = ?, " +
                    "color = ?, " +
                    "price = ?, " +
                    "doors = ?, " +
                    "size = ?, " +
                    "power = ?, " +
                    "automatic = ?, " +
                    "fuel = ?, " +
                    "image = ? " +
                    "WHERE car_id = ?");
            st.setString(1, cm.getLicence_plate());
            st.setString(2, cm.getMake());
            st.setString(3, cm.getModel());
            st.setInt(4, cm.getYear());
            st.setInt(5, cm.getEngine_capacity());
            st.setString(6, cm.getColor());
            st.setDouble(7, cm.getPrice());
            st.setInt(8, cm.getDoors());
            st.setString(9, String.valueOf(cm.getSize().charAt(0)));
            st.setInt(10, cm.getPower());
            st.setBoolean(11, cm.isAutomatic());
            st.setString(12, cm.getFuel());
            st.setString(13, cm.getImage());
            st.setString(14, String.valueOf(id));
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<CarModel> searchCars(Integer year, String make, String model, Boolean automatic, Double price, Integer power, Integer doors) {
        List<CarModel> cars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars WHERE year >= " + year +
                                                " OR make LIKE '%" + make + "%'" +
                                                " OR model LIKE '%" + model + "%'" +
                                                " OR automatic = " + automatic +
                                                " OR price <= " + price +
                                                " OR power <= " + power +
                                                " OR doors = " + doors);
            while(rs.next()){
                CarModel newCar = new CarModel(UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14)
                );
                cars.add(newCar);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }


    public List<CarModel> searchAvailableCars(Date startDate, Date endDate, Integer year, String make, String model, Boolean automatic, Double price, Integer power, Integer doors) {
        List<CarModel> cars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars LEFT JOIN contracts ON cars.car_id = contracts.car_id WHERE ((start_date >= '" +
                      startDate + "' AND end_date <= '" + endDate + "')" +
                    " OR (start_date <= '" + startDate + "' OR end_date >= '" + endDate + "')" +
                    " OR (start_date IS NULL OR end_date IS NULL))" +
                    " AND (year >= " + year +
                    " OR make LIKE '%" + make + "%'" +
                    " OR model LIKE '%" + model + "%'" +
                    " OR automatic = " + automatic +
                    " OR price <= " + price +
                    " OR power <= " + power +
                    " OR doors = " + doors + ")");
            while(rs.next()){
                CarModel newCar = new CarModel(UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14)
                );
                cars.add(newCar);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }
}
