// EmpresaService.java
package com.mycompany.fct_project;

import java.sql.SQLException;
import java.util.List;

public class EmpresaService {

    private EmpresaDAO dao;

    public EmpresaService(EmpresaDAO dao) {
        this.dao = dao;
    }

    public void addEmpresa(Empresa empresa) throws SQLException {
        dao.addEmpresa(empresa);
    }

    public List<Empresa> getAllEmpresas() throws SQLException {
        return dao.getAllEmpresas();
    }
}
