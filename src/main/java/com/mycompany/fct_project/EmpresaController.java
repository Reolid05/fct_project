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
        String CIF = view.getCIF();
        String nombre = view.getNombre();
        String telefono = view.getTelefono();
        String direccion = view.getDireccion();
        String sector = view.getSector();
        String tecnologias = view.getTecnologias();
        String numEmpleadosString = view.getNumEmpleados();
        int numEmpleados;

        // Verificar si se proporciona un valor válido para el número de empleados
        if (numEmpleadosString.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, introduzca el número de empleados", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si no se proporciona un número válido de empleados
        }

        try {
            numEmpleados = Integer.parseInt(numEmpleadosString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Por favor, introduzca un número válido de empleados", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si no se proporciona un número válido de empleados
        }




        // Validar que al menos uno de los campos no esté vacío
        if (CIF.isEmpty() && nombre.isEmpty() && telefono.isEmpty() && direccion.isEmpty() && sector.isEmpty() && tecnologias.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, complete al menos uno de los campos antes de añadir la empresa", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si todos los campos están vacíos
        }

        Empresa empresa = new Empresa(CIF, nombre, telefono, direccion, sector, tecnologias, numEmpleados);

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



