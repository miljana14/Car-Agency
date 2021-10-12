package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.UserModel;

import java.util.List;

public interface UserDao {
    List<UserModel> getAllUsers();
}
