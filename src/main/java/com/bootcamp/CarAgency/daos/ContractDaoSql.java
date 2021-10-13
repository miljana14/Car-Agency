package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.ContractModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ContractDaoSql implements ContractDao{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public List<ContractModel> getAllContracts() {
        return null;
    }

    //@Override
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

}
