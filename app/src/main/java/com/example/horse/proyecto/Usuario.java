package com.example.horse.proyecto;

/**
 * Created by jean on 22/05/16.
 */
public class Usuario {

    public Usuario(){
        setCorreo("");
        setContraseña("");
    }

    public Usuario(String correo, String contraseña){
        this.setCorreo(correo);
        this.setContraseña(contraseña);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    private String correo;
    private String contraseña;
}
