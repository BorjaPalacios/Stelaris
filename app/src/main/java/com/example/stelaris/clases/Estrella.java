package com.example.stelaris.clases;

public class Estrella {

    private String nombre;
    private String descripcion;
    private boolean hijos;

    public Estrella(String nombre, String descripcion, boolean hijos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hijos = hijos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isHijos() {
        return hijos;
    }

    public void setHijos(boolean hijos) {
        this.hijos = hijos;
    }
}
