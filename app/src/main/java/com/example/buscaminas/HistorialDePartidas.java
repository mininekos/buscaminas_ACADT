package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.buscaminas.BBDD.DbHelper;
import com.example.buscaminas.ReciclyView.AdaptadorDatos;
import com.example.buscaminas.ReciclyView.ItemLista;
import com.example.buscaminas.Recursos.PuntuacionPartida;
import com.example.buscaminas.databinding.ActivityHistorialDePartidasBinding;

import java.util.ArrayList;

public class HistorialDePartidas extends AppCompatActivity implements View.OnClickListener {

    private ActivityHistorialDePartidasBinding binding;
    private AdaptadorDatos adaptadorDatos;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PuntuacionPartida> partidas;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistorialDePartidasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnVolverHistorial.setOnClickListener(this);

        dbHelper = new DbHelper(HistorialDePartidas.this);
        partidas = dbHelper.obtenerPartidas();
        linearLayoutManager = new LinearLayoutManager(HistorialDePartidas.this);

        rellenarRecycler();
    }

    private void rellenarRecycler() {
        binding.rvHistorial.setLayoutManager(linearLayoutManager);
        adaptadorDatos = new AdaptadorDatos();
        binding.rvHistorial.setAdapter(adaptadorDatos);

        for (int i = 0; i < partidas.size(); i++) {
            adaptadorDatos.add(new ItemLista(partidas.get(i).getFecha(), (partidas.get(i).getPuntuacion() + "")));
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}