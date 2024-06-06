package com.mycompany.fct_project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmpresaController {
    private EmpresaView view;
    private EmpresaService service;

    public EmpresaController(EmpresaView view, EmpresaService service) {
        this.view = view;
        this.service = service;

        this.view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmpresa();
            }
        });

        loadEmpresas();
    }

    private void addEmpresa() {
        String idempresa = view.getidempresa();
        String nombre = view.getNombre();
        String sector = view.getSector();


        // Validar que al menos uno de los campos no esté vacío
        if (idempresa.isEmpty() && nombre.isEmpty() && sector.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete al menos uno de los campos antes de añadir la empresa", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si todos los campos están vacíos
        }

        Empresa empresa = new Empresa(idempresa, nombre, sector);

        try {
            service.addEmpresa(empresa);
            JOptionPane.showMessageDialog(view, "Empresa añadida correctamente.");
            loadEmpresas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al añadir empresa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmpresas() {
        try {
            view.getEmpresaComboBox().removeAllItems();
            List<Empresa> empresas = service.getAllEmpresas();
            for (Empresa empresa : empresas) {
                view.getEmpresaComboBox().addItem(empresa.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar empresas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



