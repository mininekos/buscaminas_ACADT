package com.example.buscaminas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.buscaminas.databinding.ActivityConfiguracionBinding;

public class Configuracion extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    private ActivityConfiguracionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConfiguracionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        preferencias=getSharedPreferences("Todo", Context.MODE_PRIVATE);
        editor = preferencias.edit();
        binding.btnFacil.setOnClickListener(this);
        binding.btnMedio.setOnClickListener(this);
        binding.btnDificil.setOnClickListener(this);
        binding.btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(R.id.btnFacil==view.getId()){
            editor.putInt("Minas",8);
            editor.commit();
            crearDialogo(8).show();
        }
        if(R.id.btnMedio==view.getId()){
            editor.putInt("Minas",12);
            editor.commit();
            crearDialogo(12).show();
        }
        if(R.id.btnDificil==view.getId()){
            editor.putInt("Minas",16);
            editor.commit();
            crearDialogo(16).show();
        }
        if(R.id.btnVolver==view.getId()){
            finish();
        }
    }
    private AlertDialog.Builder crearDialogo(int numBombas) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Dificultad");
        dialogo1.setMessage("En el tablero hay "+numBombas+" bombas");
        dialogo1.setCancelable(true);

        dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return dialogo1;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}