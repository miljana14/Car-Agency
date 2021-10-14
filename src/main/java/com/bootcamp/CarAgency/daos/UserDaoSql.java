package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.database.DatabaseConnection;
import com.bootcamp.CarAgency.models.CarRequestModel;
import com.bootcamp.CarAgency.models.UserModel;
import com.bootcamp.CarAgency.models.UserGetResponseModel;
import com.bootcamp.CarAgency.models.UserRequestModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDaoSql implements UserDao{
    static Connection conn = DatabaseConnection.getConnection();

    @Override
    public UserModel getUser(UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            rs.next();
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
            return newUser;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

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

    @Override
    public List<UserGetResponseModel> getAllResponseUsers() {
        List<UserGetResponseModel> allUsers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while(rs.next()){
                UserGetResponseModel newUser = new UserGetResponseModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                allUsers.add(newUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }

    public void update(UserRequestModel um, UUID id) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE users " +
                    "SET username = ?, " +
                    "password = ?, " +
                    "first_name = ?, " +
                    "last_name = ?, " +
                    "phone_number = ?, " +
                    "image = ? " +
                    "WHERE user_id = ?");
            st.setString(1, um.getUsername());
            st.setString(2, um.getPassword());
           // st.setString(3, um.getNewPassword());
            st.setString(3, um.getFirst_name());
            st.setString(4, um.getLast_name());
            st.setString(5, um.getPhone_number());
            st.setString(6, um.getImage());
            st.setString(7, String.valueOf(id));
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
