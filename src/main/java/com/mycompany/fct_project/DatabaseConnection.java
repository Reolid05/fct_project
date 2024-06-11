package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://192.168.1.10:5432/DB_FCT?charset=utf8";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Virtual01";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
