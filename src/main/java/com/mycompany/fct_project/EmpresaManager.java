package com.mycompany.fct_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaManager extends JFrame {
    private static final String URL = "jdbc:postgresql://192.168.1.10:5432/DB_FCT";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Virtual01";

    private JComboBox<String> empresaComboBox;
    private JTextField idempresaField;
    private JTextField nombreField;
    private JComboBox<Sector> sectorComboBox;
    private JButton addButton;

    public EmpresaManager() {
        setTitle("Gestión de Empresas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("CIF:"));
        idempresaField = new JTextField();
        panel.add(idempresaField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Sector:"));
        sectorComboBox = new JComboBox<>();
        panel.add(sectorComboBox);

        addButton = new JButton("Añadir Empresa");
        panel.add(addButton);

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Empresas:"));
        empresaComboBox = new JComboBox<>();
        comboBoxPanel.add(empresaComboBox);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(comboBoxPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmpresa();
            }
        });

        loadEmpresas();
        loadSectores();
    }

    private static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }

    private void addEmpresa() {
        String idempresa = idempresaField.getText();
        String nombre = nombreField.getText();
        Sector selectedSector = (Sector) sectorComboBox.getSelectedItem();

        if (idempresa.isEmpty() && nombre.isEmpty() && selectedSector == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete al menos uno de los campos antes de añadir la empresa", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empresa empresa = new Empresa(idempresa, nombre, String.valueOf(selectedSector.getId()));

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Empresa (idempresa, nombre, sector) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, empresa.getIdempresa());
            preparedStatement.setString(2, empresa.getNombre());
            preparedStatement.setInt(3, Integer.parseInt(empresa.getSector()));
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Empresa añadida correctamente.");
            loadEmpresas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al añadir empresa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmpresas() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Empresa");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            empresaComboBox.removeAllItems();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                empresaComboBox.addItem(nombre);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar empresas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSectores() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT idsector, descripcion FROM Sector");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            sectorComboBox.removeAllItems();
            while (resultSet.next()) {
                int id = resultSet.getInt("idsector");
                String descripcion = resultSet.getString("descripcion");
                sectorComboBox.addItem(new Sector(id, descripcion));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar sectores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static class Empresa {
        private String idempresa;
        private String nombre;
        private String sector;

        public Empresa(String idempresa, String nombre, String sector) {
            this.idempresa = idempresa;
            this.nombre = nombre;
            this.sector = sector;
        }

        public String getIdempresa() {
            return idempresa;
        }

        public void setIdempresa(String idempresa) {
            this.idempresa = idempresa;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }
    }

    public static class Sector {
        private int id;
        private String descripcion;

        public Sector(int id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }

        public int getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }
}
