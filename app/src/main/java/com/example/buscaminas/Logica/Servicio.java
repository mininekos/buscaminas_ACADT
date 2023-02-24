package com.example.buscaminas.Logica;

import com.example.buscaminas.Recursos.Casilla;

public class Servicio {


    private static Servicio servicio;
    private int dimTablero;
    private static Casilla[][] casillas;
    private static int numMinas;

    private  Servicio(int dimTablero) {
        this.dimTablero=dimTablero;

    }


    public static Servicio getServicio(int dimTablero, int numMinass, Casilla[][] casillass){
        casillas = casillass;
        numMinas=numMinass;
        if(servicio==null){
            servicio=new Servicio(dimTablero);
        }
        return servicio;
    }


    private int contarCoordenada(int fila, int columna){
        int cantidadDeBombas = 0;

        if (fila - 1 >= 0 && columna - 1 >=0){
            if (casillas[fila-1][columna-1].contenido==100){
                cantidadDeBombas++;
            }
        }

        if (fila - 1 >= 0){
            if (casillas[fila-1][columna].contenido==100){
                cantidadDeBombas++;
            }
        }

        if (fila - 1 >= 0 && columna + 1 <dimTablero){
            if (casillas[fila-1][columna+1].contenido==100){
                cantidadDeBombas++;
            }
        }

        if ( columna + 1 <dimTablero){
            if (casillas[fila][columna+1].contenido==100){
                cantidadDeBombas++;
            }
        }

        if (fila + 1 <dimTablero && columna + 1 <dimTablero){
            if (casillas[fila+1][columna+1].contenido==100){
                cantidadDeBombas++;
            }
        }

        if (fila + 1 <dimTablero ){
            if (casillas[fila+1][columna].contenido==100){
                cantidadDeBombas++;
            }
        }

        if (fila + 1 <dimTablero && columna - 1 >=0){
            if (casillas[fila+1][columna-1].contenido==100){
                cantidadDeBombas++;
            }
        }

        if ( columna - 1 >=0){
            if (casillas[fila][columna-1].contenido==100){
                cantidadDeBombas++;
            }
        }

        return cantidadDeBombas;
    }

    private void contarBombasDelPerimetro() {
        for (int i = 0; i < dimTablero; i++) {
            for (int j = 0; j < dimTablero; j++) {
                if (casillas[i][j].contenido == 0) {
                    casillas[i][j].contenido = contarCoordenada(i, j);
                }
            }
        }
    }

    private void colocarMinas(){
        int cantidadDeMinasPorColocar = numMinas;
        while (cantidadDeMinasPorColocar>0){
            int fila = (int) (Math.random()*dimTablero);
            int columna = (int) (Math.random()*dimTablero);
            if (casillas[fila][columna].contenido == 0){
                casillas[fila][columna].contenido = 100;
                cantidadDeMinasPorColocar --;
            }
        }
    }

    public boolean ganar(){
        int cantidad = 0;
        for (int i = 0; i< dimTablero; i++){
            for (int j = 0; j<dimTablero; j++){
                if (casillas[i][j].destapada){
                    cantidad++;
                }
            }
        }

        if (cantidad == (dimTablero*dimTablero- numMinas)){
            return true;
        } else {
            return false;
        }
    }

    public Casilla[][] destaparBombas(){
        for (int i = 0; i<dimTablero; i++){
            for (int j = 0; j < dimTablero; j++){
                casillas[i][j].banderita = false;
                if (casillas[i][j].contenido==100){
                    casillas[i][j].destapada = true;
                }
            }
        }
        return casillas;
    }

    public Casilla[][] reiniciarJuego(){
        casillas = new Casilla[dimTablero][dimTablero];
        for (int i = 0; i<dimTablero; i++){
            for (int j = 0; j<dimTablero; j++){
                casillas[i][j] = new Casilla();
            }
        }
        colocarMinas();
        contarBombasDelPerimetro();
        return casillas;
    }

}
