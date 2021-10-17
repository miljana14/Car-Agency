package com.bootcamp.CarAgency.controllers;

import com.bootcamp.CarAgency.daos.UserDaoSql;

import com.bootcamp.CarAgency.models.users.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    UserDaoSql ud = new UserDaoSql();

    static String password = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$";
    static String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static String phoneNumber = "^[+][0-9]{1,4}[0-9]{5,8}$";

    public static boolean isValid(String s, String regex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isValidLength(String s, int num){
        return s.length() >= num;
    }

    public static boolean isValidLengthExact(String s, int num){
        return s.length() == num;
    }

    @GetMapping("/users/{user_id}")
    public UserModel getUser(@PathVariable("user_id") UUID id){
        for (var x : ud.getAllUsers()){
            if (x.getUser_id().equals(id))
                return ud.getUser(id);
        }
        return null;
    }

    @GetMapping("/users")
    public List<UserGetResponseModel> getAllUsers(@RequestHeader("idAdmin") UUID id){
        if (getUser(id).isAdmin()){
            return ud.getAllResponseUsers();
        }
        return null;
    }

    @PatchMapping("/users/{user_id}")
    public UserResponseModel update(@RequestBody UserRequestModel user, @PathVariable("user_id") UUID id){
            String oldPass = user.getPassword();
            String newPass = user.getNewPassword();

            for (var x : ud.getAllUsers()) {
                if (!getUser(id).isAdmin()){
                if (oldPass.equals(x.getPassword())) {
                    if (isValid(newPass,password)) {
                        user.setPassword(newPass);
                        ud.update(user, id);
                        return new UserResponseModel(true, "Successfully updated");
                    }
                }
                }
            }
        return new UserResponseModel(false,"Password not valid!");
    }

    @PostMapping("/users/login")
    public LoginResponseModel login(@RequestBody LoginRequestModel info){
        for (var x : ud.getAllUsers()){
            if (ud.login(info.getIdentification(),info.getPassword())){
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
        if (isValid(info.getEmail(),email) && isValidLength(info.getUsername(),3) && isValid(info.getPassword(),password)){
            ud.register(info);
            return new RegisterResponseModel(true,info.getUsername() + " - " + info.getEmail() +" is successfully registrated!");
        }
        return new RegisterResponseModel(false,"Username is already taken!");
    }

    @PatchMapping("/admin/update/{user_id}")
    public AdminResponseModel adminUpdate(@RequestBody AdminRequestModel admin, @PathVariable("user_id") UUID id, @RequestHeader("idAdmin") UUID adminId){
            if (!getUser(adminId).isAdmin()) {
                return new AdminResponseModel(false,"You are not admin!");
            }
        List<String> changed = new ArrayList<>();
        for (var x : ud.getAllUsers()) {
            if (x.getUsername().equals(admin.getUsername()) || x.getEmail().equals(admin.getEmail()) ||
                    x.getPersonal_number().equals(admin.getPersonal_number())){
                return new AdminResponseModel(false,"Unique value!");
            }

            if (isValid(admin.getEmail(), email) && isValidLength(admin.getEmail(), 3)) {
                changed.add(admin.getEmail());
            }
            if (isValidLength(admin.getUsername(), 3)) {
                changed.add(admin.getUsername());
            }
            if (isValidLength(admin.getFirst_name(), 1)) {
                changed.add(admin.getFirst_name());
            }
            if (isValidLength(admin.getLast_name(), 1)) {
                changed.add(admin.getLast_name());
            }
            if (isValidLengthExact(admin.getPersonal_number(), 9)) {
                changed.add(admin.getPersonal_number());
            }
            if (isValid(admin.getPhone_number(), phoneNumber)) {
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
