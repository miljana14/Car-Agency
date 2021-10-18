package com.bootcamp.CarAgency.services;

import com.bootcamp.CarAgency.daos.UserDaoSql;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserService {

    public boolean isValid(String s, String regex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean isValidLength(String s, int num){
        return s.length() >= num;
    }

    public boolean isValidLengthExact(String s, int num){
        return s.length() == num;
    }


    public byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }


    UserDaoSql ud = new UserDaoSql();
    public boolean getAdmin(UUID id) {
        for (var x : ud.getAllUsers()) {
            if (x.getUser_id().equals(id) && x.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}
