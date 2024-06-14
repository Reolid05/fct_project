package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://192.168.1.10:5432/DB_FCT";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Virtual01";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            System.out.println("Conexión exitosa a la base de datos PostgreSQL!");
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
            return false;
        }
    }
}
