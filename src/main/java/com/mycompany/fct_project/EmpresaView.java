package com.mycompany.fct_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmpresaView extends JFrame {
    private JComboBox<String> empresaComboBox;
    private JTextField cifField;
    private JTextField nombreField;
    private JTextField telefonoField;
    private JTextField direccionField;
    private JTextField sectorField;
    private JTextField tecnologiasField;
    private JTextField numEmpleadosField;
    private JButton addButton;

    public EmpresaView() {
        setTitle("Gestión de Empresas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de entrada de datos
        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("CIF:"));
        cifField = new JTextField();
        panel.add(cifField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        panel.add(telefonoField);

        panel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        panel.add(direccionField);

        panel.add(new JLabel("Sector:"));
        sectorField = new JTextField();
        panel.add(sectorField);

        panel.add(new JLabel("Tecnologías:"));
        tecnologiasField = new JTextField();
        panel.add(tecnologiasField);

        panel.add(new JLabel("Número de Empleados:"));
        numEmpleadosField = new JTextField();
        panel.add(numEmpleadosField);

        addButton = new JButton("Añadir Empresa");
        panel.add(addButton);

        // Panel de lista desplegable
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Empresas:"));
        empresaComboBox = new JComboBox<>();
        comboBoxPanel.add(empresaComboBox);

        // Añadir paneles al frame
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(comboBoxPanel, BorderLayout.SOUTH);
    }

    public String getCIF() {
        return cifField.getText();
    }

    public String getNombre() {
        return nombreField.getText();
    }

    public String getTelefono() {
        return telefonoField.getText();
    }

    public String getDireccion() {
        return direccionField.getText();
    }

    public String getSector() {
        return sectorField.getText();
    }

    public String getTecnologias() {
        return tecnologiasField.getText();
    }

    public String getNumEmpleados() {
        return numEmpleadosField.getText().trim();
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JComboBox<String> getEmpresaComboBox() {
        return empresaComboBox;
    }
    
    public void mostrarInformacionEmpresa(Empresa empresa) {
        cifField.setText(empresa.getCIF());
        nombreField.setText(empresa.getNombre());
        telefonoField.setText(empresa.getTelefono());
        direccionField.setText(empresa.getDireccion());
        sectorField.setText(empresa.getSector());
        tecnologiasField.setText(empresa.getTecnologias());
        numEmpleadosField.setText(String.valueOf(empresa.getNumEmpleados()));
    }
}
