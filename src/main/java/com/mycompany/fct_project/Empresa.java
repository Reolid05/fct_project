package com.mycompany.fct_project;

public class Empresa {
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