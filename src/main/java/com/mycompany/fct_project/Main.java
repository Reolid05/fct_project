package com.mycompany.fct_project;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Conexion a la base de datos exitosa.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                EmpresaManager view = new EmpresaManager();
                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

