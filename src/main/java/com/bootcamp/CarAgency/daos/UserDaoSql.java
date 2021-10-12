package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.CarModel;
import com.bootcamp.CarAgency.models.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDaoSql implements UserDao{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> allUsers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while(rs.next()){
                UserModel newUser = new UserModel(
                        UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getBoolean(10)
                );
                allUsers.add(newUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }
}
