package com.mycompany.fct_project;

public class Empresa {
    private String CIF;
    private String nombre;
    private String telefono;
    private String direccion;
    private String sector;
    private String tecnologias;
    private int numEmpleados;

    public Empresa(String CIF, String nombre, String telefono, String direccion, String sector, String tecnologias, int numEmpleados) {
        this.CIF = CIF;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sector = sector;
        this.tecnologias = tecnologias;
        this.numEmpleados = numEmpleados;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(String tecnologias) {
        this.tecnologias = tecnologias;
    }

    public int getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(int numEmpleados) {
        this.numEmpleados = numEmpleados;
    }
}