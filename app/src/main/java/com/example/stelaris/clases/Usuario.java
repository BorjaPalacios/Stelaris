package com.example.stelaris.clases;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private String username, password, email;
    private Bitmap photo;
    private BasePlanet planet;
    private ArrayList<String> favoritos;

    public Usuario(String username, String password, String email, Bitmap photo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.photo = photo;
        this.planet = BasePlanet.tierra;
        this.favoritos = new ArrayList<>();
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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPlanet() {
        return planet.toString();
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
