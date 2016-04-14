package com.example.horse.proyecto;

/**
 * Created by horse on 10/04/16.
 */
public class Reporte {

    private String tipo;
    private String descripcion;
    private String ubicacion;
    private String fecha;


    public Reporte(String tipo, String descripcion, String ubicacion, String fecha) {
        this.setTipo(tipo);
        this.setDescripcion(descripcion);
        this.setUbicacion(ubicacion);
        this.setFecha(fecha);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
