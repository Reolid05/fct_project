package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAOImpl implements EmpresaDAO {

    private static final String INSERT_EMPRESA_SQL = "INSERT INTO Empresa (idempresa, nombre, sector) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_EMPRESAS_SQL = "SELECT * FROM Empresa";

    @Override
    public void addEmpresa(Empresa empresa) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPRESA_SQL)) {
            preparedStatement.setString(1, empresa.getIdempresa());
            preparedStatement.setString(2, empresa.getNombre());
            preparedStatement.setString(3, empresa.getSector());
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
                String idempresa = resultSet.getString("idempresa");
                String nombre = resultSet.getString("nombre");
                String sector = resultSet.getString("sector");
                empresas.add(new Empresa(idempresa, nombre, sector));
            }
        }
        return empresas;
    }
}

