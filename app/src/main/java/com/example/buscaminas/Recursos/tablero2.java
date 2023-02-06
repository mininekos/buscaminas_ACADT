package com.example.buscaminas.Recursos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class tablero2 {

    private Context context;
    private Casilla[][] casillas;
    public tablero2(Context context) {
        this.context=context;
    }

//    public void onDraw(Canvas canvas) {
//        canvas.drawRGB(0, 0, 0);
//        int anchoTablero = 0;
//        if (canvas.getWidth() < canvas.getHeight())
//            anchoTablero = fondo.getWidth();
//        else
//            anchoTablero = fondo.getHeight();
//        int anchocua = anchoTablero / 8;
//        Paint paint = new Paint();
//        paint.setTextSize(20);
//        Paint paint2 = new Paint();
//        paint2.setTextSize(20);
//        paint2.setTypeface(Typeface.DEFAULT_BOLD);
//        paint2.setARGB(255, 0, 0, 255);
//        Paint paintlinea1 = new Paint();
//        paintlinea1.setARGB(255, 255, 255, 255);
//        int filaact = 0;
//        for (int f = 0; f < 8; f++) {
//            for (int c = 0; c < 8; c++) {
//                casillas[f][c].fijarxy(c * anchocua, filaact, anchocua);
//                if (casillas[f][c].destapado == false)
//                    paint.setARGB(153, 204, 204, 204);
//                else
//                    paint.setARGB(255, 153, 153, 153);
//                canvas.drawRect(c * anchocua, filaact, c * anchocua
//                        + anchocua - 2, filaact + anchocua - 2, paint);
//                // linea blanca
//                canvas.drawLine(c * anchocua, filaact, c * anchocua
//                        + anchocua, filaact, paintlinea1);
//                canvas.drawLine(c * anchocua + anchocua - 1, filaact, c
//                                * anchocua + anchocua - 1, filaact + anchocua,
//                        paintlinea1);
//
//                if (casillas[f][c].contenido >= 1
//                        && casillas[f][c].contenido <= 8
//                        && casillas[f][c].destapado)
//                    canvas.drawText(
//                            String.valueOf(casillas[f][c].contenido), c
//                                    * anchocua + (anchocua / 2) - 8,
//                            filaact + anchocua / 2, paint2);
//
//                if (casillas[f][c].contenido == 80
//                        && casillas[f][c].destapado) {
//                    Paint bomba = new Paint();
//                    bomba.setARGB(255, 255, 0, 0);
//                    canvas.drawCircle(c * anchocua + (anchocua / 2),
//                            filaact + (anchocua / 2), 8, bomba);
//                }
//
//            }
//            filaact = filaact + anchocua;
//        }
//    }
}
