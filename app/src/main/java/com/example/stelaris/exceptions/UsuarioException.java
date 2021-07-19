package com.example.stelaris.exceptions;

public class UsuarioException extends Exception{

    public String camp;
    public UsuarioException(String msg, String camp){
        super(msg);
        this.camp = camp;
    }
}
