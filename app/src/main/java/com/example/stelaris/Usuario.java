package com.example.stelaris;

import android.graphics.Bitmap;

public class Usuario {

    private String username, password, email;
    private Bitmap photo;

    public Usuario(String username, String password, String email, Bitmap photo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.photo = photo;
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
}
