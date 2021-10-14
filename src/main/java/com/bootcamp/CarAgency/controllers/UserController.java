package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.UserDaoSql;

import com.bootcamp.CarAgency.models.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    UserDaoSql ud = new UserDaoSql();

    public static boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(password);
        return m.matches();
    }

    @GetMapping("/users/{user_id}")
    public UserModel getCar(@PathVariable("user_id") UUID id){
        for (var x : ud.getAllUsers()){
            if (x.getUser_id().equals(id))
                return ud.getUser(id);
        }
        return null;
    }

    @GetMapping("/users")
    public List<UserGetResponseModel> getAllUsers(@RequestHeader("idAdmin") UUID id){
        if (getCar(id).isAdmin()){
            return ud.getAllResponseUsers();
        }
        return null;
    }

    @PatchMapping("/users/{user_id}")
    public UserResponseModel update(@RequestBody UserRequestModel user, @PathVariable("user_id") UUID id){
            String oldPass = user.getPassword();
            String newPass = user.getNewPassword();

            for (var x : ud.getAllUsers()) {
                if (!getCar(id).isAdmin()){
                if (oldPass.equals(x.getPassword())) {
                    if (isValidPassword(newPass)) {
                        user.setPassword(newPass);
                        ud.update(user, id);
                        return new UserResponseModel(true, "Successfully updated");
                    }
                }
                }
            }
        return new UserResponseModel(false,"Password not valid!");
    }
}
