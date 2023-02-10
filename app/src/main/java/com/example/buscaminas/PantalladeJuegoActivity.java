package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PantalladeJuegoActivity extends AppCompatActivity {

    private PantallaJuego pantalla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        pantalla= new PantallaJuego(PantalladeJuegoActivity.this);
        setContentView(pantalla);
    }

    @Override
    public void onBackPressed() {
        pantalla.destroy();
        finish();
    }
}