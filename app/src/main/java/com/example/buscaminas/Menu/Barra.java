package com.example.buscaminas.Menu;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Barra extends Componente{

    private float largo,ancho;

    public Barra(float pos_X, float pos_Y, int color, float largo, float ancho) {
        super(pos_X, pos_Y,color);
        this.largo = largo;
        this.ancho = ancho;
    }


    public float getLargo() {
        return largo;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p= new Paint();
        p.setAntiAlias(true);
        p.setColor(getColor());
        canvas.drawRect(this.getPos_X(),this.getPos_Y(),this.getPos_X()+this.getLargo(),
                this.getPos_Y()+this.getAncho(),p);
    }
}
