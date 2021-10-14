package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.UserModel;
import com.bootcamp.CarAgency.models.UserGetResponseModel;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    UserModel getUser(UUID id);
    List<UserModel> getAllUsers();
    List<UserGetResponseModel> getAllResponseUsers();
}
