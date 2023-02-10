package com.example.buscaminas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.buscaminas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //Esto es lo q se deberia de quedar
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnJugar.setOnClickListener(this);
        binding.btnConfiguracion.setOnClickListener(this);
        binding.btnSalir.setOnClickListener(this);

        preferencias=getSharedPreferences("Todo", Context.MODE_PRIVATE);
        editor = preferencias.edit();
        editor.putInt("Minas",8);
        editor.commit();
        //Juego quitar al terminar las pruebas
//        PantallaJuego pantalla= new PantallaJuego(this);
//        setContentView(pantalla);
    }

    @Override
    public void onClick(View view) {
        if(R.id.btnJugar==view.getId()){
            Intent i = new Intent(MainActivity.this,PantalladeJuegoActivity.class);
            startActivity(i);
        }
        if(R.id.btnConfiguracion==view.getId()){
            Intent i = new Intent(MainActivity.this,Configuracion.class);
            startActivity(i);
        }
        if(R.id.btnSalir==view.getId()){
            crearDialogo().show();
        }
    }

    private AlertDialog.Builder crearDialogo() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Cerrar");
        dialogo1.setMessage("Desea cerrar la aplicacion");
        dialogo1.setCancelable(true);

        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });

        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return dialogo1;
    }

    @Override
    public void onBackPressed() {
        crearDialogo().show();
    }

}