package com.example.buscaminas.Recursos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class Tablero {

    private Context context;
    private Casilla[][] casillas;
    private int width;
    private int height;
    private int dimTablero;


    public Tablero(Context context,int width, int height, int dimTablero, Casilla[][] casillas) {
        this.context=context;
        this.width=width;
        this.height=height;
        this.dimTablero=dimTablero;
        this.casillas=casillas;
    }

    public void onDraw(Canvas canvas){

        canvas.drawRGB(0,0,0);

        int anchoTablero = width;
        int altoTablero = height;
//        if (canvas.getWidth()<canvas.getHeight()){
//            anchoTablero = width;
//            altoTablero = height;
//        } else {
//            anchoTablero = height;
//            altoTablero = width;
//        }

        int anchoCasilla = anchoTablero/8;


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(40);

        Paint paintLinea1 = new Paint();
        paintLinea1.setAntiAlias(true);
        paintLinea1.setARGB(255,255,255,255);

        int filaActual = (altoTablero-anchoTablero)/4 ;

        for (int i = 0; i < dimTablero; i++){
            for (int j = 0; j < dimTablero ; j++){
                casillas[i][j].fijarCoordenadasXY(j * anchoCasilla, filaActual, anchoCasilla);

                if (casillas[i][j].destapada){
                    paint.setARGB(255,153,153,153);
                } else {
                    paint.setARGB(153, 204,204,204);
                }

                canvas.drawRect(j*anchoCasilla, filaActual, j*anchoCasilla+anchoCasilla-1,
                        filaActual+anchoCasilla-2, paint);
                canvas.drawLine(j*anchoCasilla, filaActual, j*anchoCasilla+anchoCasilla,
                        filaActual, paintLinea1);
                canvas.drawLine(j*anchoCasilla + anchoCasilla-1, filaActual,
                        j*anchoCasilla+anchoCasilla-1, filaActual+anchoCasilla, paintLinea1);

                // Defino pinceles para escribir de distintos colores segun la cantidad de minas vecinas
                Paint blue = new Paint();
                blue.setAntiAlias(true);
                blue.setTextSize((anchoCasilla/8)*5);
                blue.setTypeface(Typeface.DEFAULT_BOLD);
                blue.setARGB(255,0,0,255);

                Paint red = new Paint();
                red.setAntiAlias(true);
                red.setTextSize((anchoCasilla/8)*5);
                red.setTypeface(Typeface.DEFAULT_BOLD);
                red.setARGB(255,255,0,0);

                Paint green = new Paint();
                green.setAntiAlias(true);
                green.setTextSize((anchoCasilla/8)*5);
                green.setTypeface(Typeface.DEFAULT_BOLD);
                green.setARGB(255,60, 118, 54);

                Paint orange = new Paint();
                orange.setAntiAlias(true);
                orange.setTextSize((anchoCasilla/8)*5);
                orange.setTypeface(Typeface.DEFAULT_BOLD);
                orange.setARGB(255,239, 129, 37);

                Paint violet = new Paint();
                violet.setAntiAlias(true);
                violet.setTextSize((anchoCasilla/8)*5);
                violet.setTypeface(Typeface.DEFAULT_BOLD);
                violet.setARGB(255,152, 47, 229);

                Paint ligthGreen = new Paint();
                ligthGreen.setAntiAlias(true);
                ligthGreen.setTextSize((anchoCasilla/8)*5);
                ligthGreen.setTypeface(Typeface.DEFAULT_BOLD);
                ligthGreen.setARGB(255,152, 47, 229);

                // En este caso, la casilla no tiene mina pero alguno de sus vecinos sí.
                if (casillas[i][j].contenido>=1 &&
                        casillas[i][j].contenido<8 &&
                        casillas[i][j].destapada) {
                    switch (casillas[i][j].contenido) {
                        case 1:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    blue);
                            break;
                        case 2:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    orange);
                            break;
                        case 3:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    green);
                            break;
                        case 4:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    violet);
                            break;
                        case 5:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    ligthGreen);
                            break;
                        default:
                            canvas.drawText(
                                    String.valueOf(casillas[i][j].contenido),
                                    j * anchoCasilla + (anchoCasilla / 2) - 20,
                                    filaActual + anchoCasilla / 2 + 30,
                                    red);
                    }
                }

                // En este caso, la casilla tiene una mina
                if (casillas[i][j].contenido == 100 &&
                        casillas[i][j].destapada){
                    canvas.drawCircle(
                            j*anchoCasilla+anchoCasilla/2,
                            filaActual+anchoCasilla/2,
                            20,
                            red);
                }

                Paint flag = new Paint();
                flag.setARGB(255, 0, 0, 0);
                flag.setTextSize((anchoCasilla/4)*3);
                flag.setTypeface(Typeface.DEFAULT_BOLD);

                if(casillas[i][j].banderita == true /*&&
                            casillas[i][j].destapada == false*/){
                    canvas.drawText(
                            "B",
                            j*anchoCasilla + (anchoCasilla/2) - 25,
                            filaActual + anchoCasilla/2 + 30,
                            flag);
                }
            }
            filaActual += anchoCasilla;
        }
    }
}
