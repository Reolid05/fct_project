package com.mycompany.fct_project;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Configuración de la conexión a la base de datos
        try {
            DatabaseConnection.getConnection();
            System.out.println("Conexion a la base de datos exitosa.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return;
        }

        // Creación de la vista, DAO, servicio y controlador
        SwingUtilities.invokeLater(() -> {
            try {
                EmpresaView view = new EmpresaView();
                EmpresaDAO dao = new EmpresaDAOImpl(); // Instanciar la implementación concreta de EmpresaDAO
                EmpresaService service = new EmpresaService(dao); // Instanciar EmpresaService con la implementación de EmpresaDAO
                EmpresaController controller = new EmpresaController(view, service);
                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
