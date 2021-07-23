package com.example.stelaris.clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private int id;
    private String username, password, email;
    private byte[] photo;
    private BasePlanet planet;
    private ArrayList<String> favoritos;

    public Usuario() {
    }

    public Usuario(String username, String password, String email, byte[] photo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.photo = photo;
        this.planet = BasePlanet.tierra;
        this.favoritos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public BasePlanet getPlanet() {
        return planet;
    }

    public void setPlanet(BasePlanet planet) {
        this.planet = planet;
    }

    public ArrayList<String> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<String> favoritos) {
        this.favoritos = favoritos;
    }
}
