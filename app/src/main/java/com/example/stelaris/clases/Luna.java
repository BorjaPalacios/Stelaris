package com.example.stelaris.clases;

public class Luna {

    private String nombre;
    private String padre;
    private String descripcion;

    public Luna(String nombre, String padre, String descripcion) {
        this.nombre = nombre;
        this.padre = padre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
