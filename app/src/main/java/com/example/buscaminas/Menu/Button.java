package com.example.buscaminas.Menu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Button extends Componente {
    private Bitmap bmp;
    private Bitmap img;
    private int largo,ancho;
    private String nombre;

    public Button(float pos_X, float pos_Y, Resources resources, int codImg, int largo, int ancho, String nombre) {
        super(pos_X, pos_Y);
        this.largo=largo;
        this.ancho=ancho;
        this.nombre=nombre;
        bmp= BitmapFactory.decodeResource(resources, codImg);
        img=bmp.createScaledBitmap(bmp,largo ,ancho,true);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(img,getPos_X(),getPos_Y(),null);
    }

    public boolean estaDentro(int x, int y) {

        if(x>getPos_X() && x<getPos_X()+getAncho()
                && y>getPos_Y() && y<getPos_Y()+getLargo()){
            return true;
        }
        return false;
    }

}