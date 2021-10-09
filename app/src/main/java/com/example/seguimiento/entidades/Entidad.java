package com.example.seguimiento.entidades;

import java.util.Date;

public class Entidad {

    private String id;
    private double latitud;
    private double longitud;
    private String hora;

    public Entidad() {
    }

    public Entidad(String id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.hora = new Date().toLocaleString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
