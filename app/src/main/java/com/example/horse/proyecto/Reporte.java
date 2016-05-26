package com.example.horse.proyecto;

/**
 * Created by horse on 10/04/16.
 */
public class Reporte {

    private String tipo;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    private String latitud;
    private String longitud;


    public Reporte(String tipo, String descripcion, String ubicacion, String fecha, String latitud, String longi) {
        this.setTipo(tipo);
        this.setLatitud(latitud);
        this.setLongitud(longi);
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
