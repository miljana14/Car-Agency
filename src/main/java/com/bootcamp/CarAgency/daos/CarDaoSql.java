package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.CarModel;
import com.bootcamp.CarAgency.models.CarRequestModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarDaoSql implements CarDao{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public void add(CarModel cm) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, UUID.randomUUID().toString());
            st.setString(2, cm.getLicence_plate());
            st.setString(3, cm.getMake());
            st.setString(4, cm.getModel());
            st.setInt(5, cm.getYear());
            st.setInt(6, cm.getEngine_capacity());
            st.setString(7, cm.getColor());
            st.setDouble(8, cm.getPrice());
            st.setInt(9, cm.getDoors());
            st.setString(10, String.valueOf(cm.getSize().charAt(0)));
            st.setInt(11, cm.getPower());
            st.setBoolean(12, cm.isAutomatic());
            st.setString(13, cm.getFuel());
            st.setString(14, cm.getImage());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM cars WHERE car_id = ?");
            st.setString(1, id.toString());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public CarModel getCar(UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM cars WHERE car_id = ?");
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            rs.next();
            CarModel newCar = new CarModel(
                    UUID.fromString(rs.getString(1)),
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
            return newCar;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CarModel> getAllCars() {
        List<CarModel> allCars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars");
            while(rs.next()){
                CarModel newCar = new CarModel(
                        UUID.fromString(rs.getString(1)),
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

                allCars.add(newCar);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCars;
    }

    //@Override
//    public void update(CarRequestModel cm) {
//        try {
//            PreparedStatement st = conn.prepareStatement("UPDATE cars " +
//                    "SET licence_plate = ?, " +
//                    "make = ?, " +
//                    "model = ?, " +
//                    "year = ?, " +
//                    "engine_capacity = ?, " +
//                    "color = ?, " +
//                    "price = ?, " +
//                    "doors = ?, " +
//                    "size = ?, " +
//                    "power = ?, " +
//                    "automatic = ?, " +
//                    "fuel = ?, " +
//                    "image = ? " +
//                    "WHERE car_id = ?");
//            st.setString(1, cm.getLicence_plate());
//            st.setString(2, cm.getMake());
//            st.setString(3, cm.getModel());
//            st.setInt(4, cm.getYear());
//            st.setInt(5, cm.getEngine_capacity());
//            st.setString(6, cm.getColor());
//            st.setDouble(7, cm.getPrice());
//            st.setInt(8, cm.getDoors());
//            st.setString(9, String.valueOf(cm.getSize().charAt(0)));
//            st.setInt(10, cm.getPower());
//            st.setBoolean(11, cm.isAutomatic());
//            st.setString(12, cm.getFuel());
//            st.setString(13, cm.getImage());
//            st.setString(14, String.valueOf(cm.getCar_id()));
//            st.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}