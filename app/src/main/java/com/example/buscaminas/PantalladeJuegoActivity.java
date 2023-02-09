package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PantalladeJuegoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        PantallaJuego pantalla= new PantallaJuego(this);
        setContentView(pantalla);
        //setContentView(R.layout.activity_pantallade_juego);
    }
}