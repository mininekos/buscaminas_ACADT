package com.example.buscaminas.Recursos;

import java.util.Objects;

public class PuntuacionPartida {
    private int id;
    private String fecha, puntuacion;

    public PuntuacionPartida(String fecha, String puntuacion) {
        this.id = id;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
    }

    public PuntuacionPartida() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }


}
