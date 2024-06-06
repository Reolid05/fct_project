package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://192.168.1.10:5432/DB_FCT";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Virtual01";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }
}

