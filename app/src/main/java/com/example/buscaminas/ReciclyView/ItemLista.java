package com.example.buscaminas.ReciclyView;

public class ItemLista {
    private String textoFecha;
    private String textoPuntuacion;

    public String getTextoFecha() {
        return textoFecha;
    }

    public void setTextoFecha(String textoFecha) {
        this.textoFecha = textoFecha;
    }

    public String getTextoPuntuacion() {
        return textoPuntuacion;
    }

    public void setTextoPuntuacion(String textoPuntuacion) {
        this.textoPuntuacion = textoPuntuacion;
    }

    public ItemLista(String textoFecha, String textoPizza) {
        this.textoFecha = textoFecha;
        this.textoPuntuacion = textoPizza;
    }
}
