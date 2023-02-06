package com.example.buscaminas.Recursos;

public class Casilla {

    public int contenido ;
    public boolean destapada;
    public boolean banderita;

    private int firstX, firstY, lastX, lastY;

    public void fijarCoordenadasXY(int initX, int fila, int anchoCasilla){
        firstX = initX;
        firstY = fila;
        lastX = firstX+anchoCasilla;
        lastY = firstY+anchoCasilla;
    }

    public boolean dentroDeLaCasilla(int x, int y){
        if (firstX<=x&& x<lastX &&
                firstY<=y && y < lastY){
            return true;
        } else {
            return false;
        }
    }
}
