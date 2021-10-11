package com.bootcamp.CarAgency.database;

import java.sql.*;

public class DatabaseConnection {
    private static Connection conn = null;

    static{
       // String url = System.getenv("JDBC_DATABASE_URL");
        String url = "jdbc:postgresql://localhost:5432/car_agency?user=postgres&password=password";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }
}
