// EmpresaDAO.java
package com.mycompany.fct_project;

import java.sql.SQLException;
import java.util.List;

public interface EmpresaDAO {
    void addEmpresa(Empresa empresa) throws SQLException;
    List<Empresa> getAllEmpresas() throws SQLException;
}
