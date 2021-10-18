package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.contracts.ContractApprovalRequestModel;
import com.bootcamp.CarAgency.models.contracts.ContractModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ContractDaoSql implements ContractDao{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public List<Date> getAllUnaviableDate(UUID carId) {
        List<Date> allUnaviableDates = new ArrayList<>();
        try {
            Date date;
            PreparedStatement st = conn.prepareStatement(" SELECT generate_series(start_date, end_date, interval '1 day')::date" +
                                                             " AS dates FROM contracts WHERE car_id = ?");
            st.setString(1,String.valueOf(carId));
            ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    date =rs.getDate(1);
                    allUnaviableDates.add(date);

                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUnaviableDates;
    }

    @Override
    public List<ContractModel> getAllContracts() {
        List<ContractModel> allContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts");
            while(rs.next()){
                ContractModel newContract = new ContractModel(
                        UUID.fromString(rs.getString(1)),
                        UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                );

                allContracts.add(newContract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContracts;
    }

    @Override
    public List<ContractModel> getAllUnapprovedContracts(){
        List<ContractModel> unapprovedContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE approved = false");
            while(rs.next()){
                ContractModel newContract = new ContractModel(
                        UUID.fromString(rs.getString(1)),
                        UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                );

                unapprovedContracts.add(newContract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return unapprovedContracts;
    }

    @Override
    public List<ContractModel> getHistoryContracts(UUID user_id){
        List<ContractModel> historyContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE user_id = '" + user_id + "'");
            while(rs.next()){
                ContractModel newContract = new ContractModel(
                        UUID.fromString(rs.getString(1)),
                        UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                );

                historyContracts.add(newContract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return historyContracts;
    }

    @Override
    public ContractModel getContract(UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM contracts WHERE user_id = ?");
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            rs.next();
            ContractModel newContract = new ContractModel(
                    UUID.fromString(rs.getString(1)),
                    UUID.fromString(rs.getString(2)),
                    UUID.fromString(rs.getString(3)),
                    rs.getDate(4),
                    rs.getDate(5),
                    rs.getDouble(6),
                    rs.getBoolean(7),
                    rs.getBoolean(8)
            );
            return newContract;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
        public void add(ContractModel cm) {
        java.sql.Date sqlDateStart=new java.sql.Date(cm.getStart_date().getTime());
        java.sql.Date sqlDateEnd=new java.sql.Date(cm.getEnd_date().getTime());
            try {
                PreparedStatement st = conn.prepareStatement("INSERT INTO contracts VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, UUID.randomUUID().toString());
                st.setString(2, cm.getUser_id().toString());
                st.setString(3, cm.getCar_id().toString());
                st.setDate(4, sqlDateStart);
                st.setDate(5, sqlDateEnd);
                st.setDouble(6, cm.getTotal_price());
                st.setBoolean(7, cm.isSigned());
                st.setBoolean(8, false);
                st.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public double price(UUID id){
        double price = 0;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT price FROM cars WHERE car_id = ?");
            st.setString(1,String.valueOf(id));
            ResultSet rs = st.executeQuery();
            rs.next();
                price += rs.getDouble(1);
        }   catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return price;
    }

    public void delete(UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM contracts WHERE contract_id = ?");
            st.setString(1, id.toString());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void update(ContractApprovalRequestModel cm, UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE contracts " +
                    "SET approved = ? WHERE contract_id = ?");
            st.setBoolean(1, true);
            st.setString(2, String.valueOf(id));
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}