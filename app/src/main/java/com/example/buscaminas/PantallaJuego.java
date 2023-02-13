package com.example.buscaminas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.buscaminas.Hilos.GameLoopThread;
import com.example.buscaminas.Logica.Servicio;
import com.example.buscaminas.Menu.Barra;
import com.example.buscaminas.Menu.Button;
import com.example.buscaminas.Menu.Componente;
import com.example.buscaminas.Recursos.Casilla;
import com.example.buscaminas.Recursos.Tablero;

import java.util.ArrayList;

public class PantallaJuego extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoopThread gameThread;
    private ArrayList<Componente> componentes;
    private Tablero tablero;
    private final int dimTablero=8;
    private Casilla[][] casillas;
    private boolean activo;
    private boolean bandera=false;
    private Context context;
    private SharedPreferences preferencias;
    private int numMinas;
    private MediaPlayer media;
    private SoundPool soundPool;
    private int musicaBomba;


    public PantallaJuego(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
        this.context=context;
        preferencias=context.getSharedPreferences("Todo", Context.MODE_PRIVATE);
        numMinas =preferencias.getInt("Minas",0);
        componentes= new ArrayList<>();
        media=MediaPlayer.create(context,R.raw.musica_fondo);
        media.setLooping(true);
        casillas= Servicio.getServicio(dimTablero,numMinas,casillas).reiniciarJuego();
        activo = true;
        media.start();
        invalidate();
        tablero= new Tablero(context,1000,2500,dimTablero,casillas,getResources());


        soundPool = new SoundPool( 1, AudioManager.STREAM_MUSIC , 0);
        musicaBomba=soundPool.load(context,R.raw.explosion,0);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawColor(Color.WHITE);
        //  Ondraw del tablero
            tablero.onDraw(canvas);
        //   Ondraw del menu
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
                        }
                        if((btnPresionado.estaDentro(x,y) && btnPresionado.getNombre().equals("Reset"))){
                            casillas= Servicio.getServicio(dimTablero,numMinas,casillas).reiniciarJuego();
                            activo = true;
                            media.start();
                            invalidate();
                            tablero.setCasillas(casillas);
                            Log.i("Reinicio","reiniciaste");
                        }
                        if((btnPresionado.estaDentro(x,y) && btnPresionado.getNombre().equals("Exit"))){
                            Log.i("Salir","salir");
                            destroy();
                        }
                    }
                }
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
                                    media.pause();
                                    soundPool.play(musicaBomba,1,1,1,0,1);

                                    casillas= Servicio.getServicio(dimTablero,numMinas,casillas).destaparBombas();
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

            if (activo && Servicio.getServicio(dimTablero,numMinas,casillas).ganar()){
                casillas= Servicio.getServicio(dimTablero,numMinas,casillas).destaparBombas();
                activo = false;
            }

        }

        return false;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        gameThread=new GameLoopThread(this);
        gameThread.setRunning(true);
        gameThread.start();

        componentes.add(new Barra(0,getHeight()*0.9F,Color.BLUE,getWidth(),getHeight()*0.1F));
        componentes.add(new Button((int)(getWidth()*0.45),(int)(getHeight()*0.91),getResources(),R.drawable.banderita,(int) (getWidth()*0.1),(int)(getHeight()*0.07),"Bandera"));
        componentes.add(new Button((int)(getWidth()*0.7),(int)(getHeight()*0.91),getResources(),R.drawable.btn_reset,(int) (getWidth()*0.2),(int)(getHeight()*0.07),"Reset"));
        componentes.add(new Button((int)(getWidth()*0.1),(int)(getHeight()*0.91),getResources(),R.drawable.btn_salir,(int) (getWidth()*0.2),(int)(getHeight()*0.07),"Exit"));

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        destroy();
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

        if (cantidad == (dimTablero*dimTablero- numMinas)){
            return true;
        } else {
            return false;
        }
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

    public void destroy(){
        boolean retry= true;
        gameThread.setRunning(false);
        while (retry){
            try {
                gameThread.join();
                retry=false;
            }
            catch (InterruptedException e){}
        }
        media.stop();
        ((Activity) context).finish();

    }



}
