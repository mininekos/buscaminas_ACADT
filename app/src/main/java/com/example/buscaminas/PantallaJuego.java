package com.example.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.buscaminas.Hilos.GameLoopThread;
import com.example.buscaminas.Menu.Barra;
import com.example.buscaminas.Menu.Button;
import com.example.buscaminas.Menu.Componente;
import com.example.buscaminas.Recursos.Casilla;
import com.example.buscaminas.Recursos.Tablero;

import java.util.ArrayList;
import java.util.Random;

public class PantallaJuego extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoopThread gameThread;
    private ArrayList<Componente> componentes;
    private Tablero tablero;
    private int dimTablero=8;
    private Casilla[][] casillas;
    private boolean activo = true;
    private boolean bandera=false;
    private Context context;
    private final int NUM_MINAS=18;



    public PantallaJuego(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
        this.context=context;
        componentes= new ArrayList<>();
        reiniciarJuego();
        tablero= new Tablero(context,1000,2500,dimTablero,casillas,getResources());

    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawColor(Color.WHITE);
        //  Ondraw del tablero
            tablero.onDraw(canvas);
        //        Ondraw del menu
        for (Componente componente: componentes) {
            componente.onDraw(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(Componente c:componentes){
                    if(c instanceof Button){
                        Button btnPresionado= (Button) c;
                        if((btnPresionado.estaDentro(x,y) && btnPresionado.getNombre().equals("Bandera"))){
                            bandera=!bandera;
                            Log.i("Bandera","estado: "+bandera);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        if (activo){
            for (int i = 0; i < dimTablero; i++){
                for (int j = 0; j<dimTablero; j++){

                    if (casillas[i][j].dentroDeLaCasilla(x,y)){
                        if(bandera) {
                            if(!casillas[i][j].destapada) {
                                casillas[i][j].banderita = !casillas[i][j].banderita;
                            }
                        }else{

                            if(!casillas[i][j].banderita){
                                casillas[i][j].destapada = true;

                                if (casillas[i][j].contenido == 100) {
                                    //Toast.makeText(this, "Boooooom! Perdiste", Toast.LENGTH_LONG).show();
                                    destaparBombas();
                                    activo = false;
                                } else if (casillas[i][j].contenido == 0) {
                                    recorrer(i, j);
                                }
                            }
                        }
                        invalidate();
                    }
                }
            }

            if (activo && ganar()){
                //Toast.makeText(this, "Felicitacinoes, ganaste!", Toast.LENGTH_LONG).show();
                destaparBombas();
                activo = false;
            }

        }

        return false;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        componentes.add(new Barra(0,getHeight()*0.9F,Color.BLUE,getWidth(),getHeight()*0.1F));
        componentes.add(new Button((int)(getWidth()*0.45),(int)(getHeight()*0.91),getResources(),R.drawable.banderita,(int) (getWidth()*0.1),(int)(getHeight()*0.07),"Bandera"));

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry= true;
        gameThread.setRunning(false);
        while (retry){
            try {
                gameThread.join();
                retry=false;
            }
            catch (InterruptedException e){}
        }
    }

    public void destaparBombas(){
        for (int i = 0; i<dimTablero; i++){
            for (int j = 0; j < dimTablero; j++){
                casillas[i][j].banderita = false;
                if (casillas[i][j].contenido==100){
                    casillas[i][j].destapada = true;
                }
            }
        }
        invalidate();
    }

    public void reiniciarJuego(){
        casillas = new Casilla[dimTablero][dimTablero];
        for (int i = 0; i<dimTablero; i++){
            for (int j = 0; j<dimTablero; j++){
                casillas[i][j] = new Casilla();
            }
        }
        colocarMinas();
        contarBombasDelPerimetro();
        activo = true;

        invalidate();
    }

    private void colocarMinas(){
        int cantidadDeMinasPorColocar = NUM_MINAS;
        if(dimTablero==12) cantidadDeMinasPorColocar = 20;
        if(dimTablero==16) cantidadDeMinasPorColocar = 40;
        while (cantidadDeMinasPorColocar>0){
            int fila = (int) (Math.random()*dimTablero);
            int columna = (int) (Math.random()*dimTablero);
            if (casillas[fila][columna].contenido == 0){
                casillas[fila][columna].contenido = 100;
                cantidadDeMinasPorColocar --;
            }
        }
    }

    private boolean ganar(){
        int cantidad = 0;
        for (int i = 0; i< dimTablero; i++){
            for (int j = 0; j<dimTablero; j++){
                if (casillas[i][j].destapada){
                    cantidad++;
                }
            }
        }

        if (cantidad == 56){
            return true;
        } else {
            return false;
        }
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

    private void recorrer(int fila, int columna){

        if (fila>=0 && fila <dimTablero && columna>=0 && columna<dimTablero){
            if (casillas[fila][columna].contenido == 0 &&
                    !casillas[fila][columna].banderita){

                casillas[fila][columna].destapada = true;
                casillas[fila][columna].contenido = 50;

                recorrer(fila-1, columna-1);
                recorrer(fila-1,columna);
                recorrer(fila-1,columna+1);
                recorrer(fila,columna+1);
                recorrer(fila+1, columna+1);
                recorrer(fila+1, columna);
                recorrer(fila+1, columna-1);
                recorrer(fila, columna-1);

            } else if (casillas[fila][columna].contenido<=8 &&
                    !casillas[fila][columna].banderita){
                casillas[fila][columna].destapada = true;
            }
        }


    }
}
