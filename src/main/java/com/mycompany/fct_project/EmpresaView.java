package com.mycompany.fct_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmpresaView extends JFrame {
    private JComboBox<String> empresaComboBox;
    private JTextField idempresaField;
    private JTextField nombreField;
    private JTextField sectorField;
    private JButton addButton;

    public EmpresaView() {
        setTitle("Gestión de Empresas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de entrada de datos
        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("CIF:"));
        idempresaField = new JTextField();
        panel.add(idempresaField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);
        
        panel.add(new JLabel("Sector:"));
        sectorField = new JTextField();
        panel.add(sectorField);


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

    public String getidempresa() {
        return idempresaField.getText();
    }

    public String getNombre() {
        return nombreField.getText();
    }

    public String getSector() {
        return sectorField.getText();
    }


    public JButton getAddButton() {
        return addButton;
    }

    public JComboBox<String> getEmpresaComboBox() {
        return empresaComboBox;
    }
    
    public void mostrarInformacionEmpresa(Empresa empresa) {
        idempresaField.setText(empresa.getIdempresa());
        nombreField.setText(empresa.getNombre());
        sectorField.setText(empresa.getSector());
    }
}
