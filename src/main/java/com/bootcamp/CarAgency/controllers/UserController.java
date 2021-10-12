package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.UserDaoSql;

import com.bootcamp.CarAgency.models.UserModel;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class UserController {
    UserDaoSql ud = new UserDaoSql();

    @GetMapping("/cars")
    public List<UserModel> getAllUsers(){
        return ud.getAllUsers();
    }
}
