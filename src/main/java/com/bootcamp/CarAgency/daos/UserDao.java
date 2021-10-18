package com.bootcamp.CarAgency.daos;

import com.bootcamp.CarAgency.models.users.*;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    UserGetResponseModel getUser(UUID id);
    List<UserModel> getAllUsers();
    List<UserGetResponseModel> getAllResponseUsers();
    void update(UserRequestModel um, UUID id);
    boolean login(String identification, String password);
    void register(RegisterRequestModel rrm);
    void adminUpdate(AdminRequestModel am, UUID id);
}
