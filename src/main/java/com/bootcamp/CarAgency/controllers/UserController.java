package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.UserDaoSql;
import com.bootcamp.CarAgency.models.users.*;
import com.bootcamp.CarAgency.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class UserController {
    UserDaoSql ud = new UserDaoSql();
    UserService us = new UserService();

    static String password = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$";
    static String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static String phoneNumber = "^[+][0-9]{1,4}[0-9]{5,8}$";



    @GetMapping("/users/{user_id}")
    public UserGetResponseModel getUser(@PathVariable("user_id") UUID id){
        return ud.getUser(id);
    }

    @GetMapping("/users")
    public List<UserGetResponseModel> getAllUsers(@RequestHeader("idAdmin") UUID id){
        if (us.getAdmin(id)){
            return ud.getAllResponseUsers();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/users/{user_id}")
    public UserResponseModel update(@RequestBody UserRequestModel user, @PathVariable("user_id") UUID id) throws NoSuchAlgorithmException {
        user.setPassword(us.toHexString(us.getSHA(user.getPassword())));
        for (var x : ud.getAllUsers()) {
            if (!us.getAdmin(id)){
                if (user.getPassword().equals(x.getPassword())) {
                    if (us.isValid(user.getNewPassword(),password)) {
                        user.setNewPassword(us.toHexString(us.getSHA(user.getNewPassword())));
                        user.setPassword(user.getNewPassword());
                        ud.update(user, id);
                        return new UserResponseModel(true, "Successfully updated");
                    }
                }
            }
        }
        return new UserResponseModel(false,"Password not valid!");
    }

    @PostMapping("/users/login")
    public LoginResponseModel login(@RequestBody LoginRequestModel info) throws NoSuchAlgorithmException {
        info.setPassword(us.toHexString(us.getSHA(info.getPassword())));
        for (var x : ud.getAllUsers()){
            if (ud.login(info.getIdentification(),info.getPassword()) && (info.getIdentification().equals(x.getEmail()) || info.getIdentification().equals(x.getUsername()))){
                return new LoginResponseModel(true, x.getUser_id().toString());
            }
        }
        return new LoginResponseModel(false,"Wrong username/email or password.");
    }

    @PostMapping("/users/register")
    public RegisterResponseModel register(@RequestBody RegisterRequestModel info){
        for (var x : ud.getAllUsers()){
            if (x.getUsername().equals(info.getUsername()) || x.getEmail().equals(info.getEmail())){
                return new RegisterResponseModel(false,"Username/email is already taken!");
            }
        }
        if (us.isValid(info.getEmail(),email) && us.isValidLength(info.getUsername(),3) && us.isValid(info.getPassword(),password)){
            try {
                info.setPassword(us.toHexString(us.getSHA(info.getPassword())));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ud.register(info);
            return new RegisterResponseModel(true,info.getUsername() + " - " + info.getEmail() +" is successfully registrated!");
        }
        return new RegisterResponseModel(false,"Username is already taken!");
    }

    @PatchMapping("/admin/update/{user_id}")
    public AdminResponseModel adminUpdate(@RequestBody AdminRequestModel admin, @PathVariable("user_id") UUID id, @RequestHeader("idAdmin") UUID adminId){
            if (!us.getAdmin(adminId)) {
                return new AdminResponseModel(false,"You are not admin!");
            }
        List<String> changed = new ArrayList<>();
        for (var x : ud.getAllUsers()) {
            if (x.getUsername().equals(admin.getUsername()) || x.getEmail().equals(admin.getEmail()) ||
                    x.getPersonal_number().equals(admin.getPersonal_number())){
                return new AdminResponseModel(false,"Unique value!");
            }

            if (us.isValid(admin.getEmail(), email) && us.isValidLength(admin.getEmail(), 3)) {
                changed.add(admin.getEmail());
            }
            if (us.isValidLength(admin.getUsername(), 3)) {
                changed.add(admin.getUsername());
            }
            if (us.isValidLength(admin.getFirst_name(), 1)) {
                changed.add(admin.getFirst_name());
            }
            if (us.isValidLength(admin.getLast_name(), 1)) {
                changed.add(admin.getLast_name());
            }
            if (us.isValidLengthExact(admin.getPersonal_number(), 9)) {
                changed.add(admin.getPersonal_number());
            }
            if (us.isValid(admin.getPhone_number(), phoneNumber)) {
                changed.add(admin.getPhone_number());
            }

            if (admin.getEmail().isEmpty()){
                admin.setEmail(getUser(id).getEmail());
            }
            if (admin.getUsername().isEmpty()){
                admin.setUsername(getUser(id).getUsername());
            }
            if (admin.getFirst_name().isEmpty()){
                admin.setFirst_name(getUser(id).getFirst_name());
            }
            if (admin.getLast_name().isEmpty()){
                admin.setLast_name(getUser(id).getLast_name());
            }
            if (admin.getPersonal_number().isEmpty()){
                admin.setPersonal_number(getUser(id).getPersonal_number());
            }
            if (admin.getPhone_number().isEmpty()){
                admin.setPhone_number(getUser(id).getPhone_number());
            }
            ud.adminUpdate(admin, id);
            return new AdminResponseModel(true, "Changed: " + changed);
            }
        return new AdminResponseModel(false,"Invalid input!");
    }
}
