package com.example.stelaris.clases;

public class Planeta {

    private String nombre;
    private String padre;
    private boolean hijos;
    private String descrpcion;

    public Planeta(String nombre, String padre, boolean hijos, String descrpcion) {
        this.nombre = nombre;
        this.padre = padre;
        this.hijos = hijos;
        this.descrpcion = descrpcion;
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

    public boolean isHijos() {
        return hijos;
    }

    public void setHijos(boolean hijos) {
        this.hijos = hijos;
    }

    public String getDescrpcion() {
        return descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }
}
