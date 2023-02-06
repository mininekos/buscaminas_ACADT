package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        PantallaJuego pantalla= new PantallaJuego(this);
        setContentView(pantalla);
    }
}