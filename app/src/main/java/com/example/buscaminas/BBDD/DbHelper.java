package com.example.buscaminas.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import com.example.buscaminas.Recursos.PuntuacionPartida;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSIONBBDD = 1;
    private static final String NOMBREBBDD = "Buscaminas.db";


    public DbHelper(@Nullable Context context) {
        super(context, NOMBREBBDD, null, VERSIONBBDD);
    }


    @Override
    public void onCreate(SQLiteDatabase bbdd) {
        bbdd.execSQL(
                "CREATE TABLE \"partida\" (\n" +
                        "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "\t\"fecha\"\tTEXT NOT NULL,\n" +
                        "\t\"puntuacion\"\tTEXT NOT NULL\n" +
                        ");"
        );

        ContentValues values = new ContentValues();
        values.put("fecha", "Fecha");
        values.put("puntuacion", "Puntuación");
        bbdd.insert("partida", null, values);

        values = new ContentValues();
        values.put("fecha", "");
        values.put("puntuacion", "");
        bbdd.insert("partida", null, values);

        System.out.println("Base de datos creada correctamente.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertarPartida(PuntuacionPartida partida) {
        long id = 0;

        try {
            SQLiteDatabase bbdd = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values = new ContentValues();
            values.put("fecha", partida.getFecha());
            values.put("puntuacion", partida.getPuntuacion());
            id = bbdd.insert("partida", null, values);

            System.out.println("Se ha introducido correctamente.");
        } catch (Exception ex) {
            System.err.println("Algo falló al insertar.");
            System.err.println(ex.getMessage());
        }

        return id;
    }

    public ArrayList<PuntuacionPartida> obtenerPartidas() {
        SQLiteDatabase bbdd = this.getWritableDatabase();

        ArrayList<PuntuacionPartida> partidas = new ArrayList<PuntuacionPartida>();
        PuntuacionPartida partida = null;
        Cursor cursorPartida = null;

        cursorPartida = bbdd.rawQuery("SELECT * FROM partida;", null);

        if (cursorPartida.moveToFirst()) {
            do {
                partida = new PuntuacionPartida();

                partida.setId(cursorPartida.getInt(0));
                partida.setFecha(cursorPartida.getString(1));
                partida.setPuntuacion(cursorPartida.getString(2));

                partidas.add(partida);
            } while (cursorPartida.moveToNext());
        }
        cursorPartida.close();

        return partidas;
    }
}