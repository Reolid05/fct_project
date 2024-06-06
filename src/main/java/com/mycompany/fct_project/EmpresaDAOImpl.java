package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAOImpl implements EmpresaDAO {

    private static final String INSERT_EMPRESA_SQL = "INSERT INTO Empresa (CIF, nombre, telefono, direccion, sector, tecnologias, numEmpleados) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EMPRESAS_SQL = "SELECT * FROM Empresa";

    @Override
    public void addEmpresa(Empresa empresa) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPRESA_SQL)) {
            preparedStatement.setString(1, empresa.getCIF());
            preparedStatement.setString(2, empresa.getNombre());
            preparedStatement.setString(3, empresa.getTelefono());
            preparedStatement.setString(4, empresa.getDireccion());
            preparedStatement.setString(5, empresa.getSector());
            preparedStatement.setString(6, empresa.getTecnologias());
            preparedStatement.setInt(7, empresa.getNumEmpleados());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Empresa> getAllEmpresas() throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPRESAS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String CIF = resultSet.getString("CIF");
                String nombre = resultSet.getString("nombre");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String sector = resultSet.getString("sector");
                String tecnologias = resultSet.getString("tecnologias");
                int numEmpleados = resultSet.getInt("numEmpleados");
                empresas.add(new Empresa(CIF, nombre, telefono, direccion, sector, tecnologias, numEmpleados));
            }
        }
        return empresas;
    }
}

