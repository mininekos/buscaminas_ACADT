package com.example.buscaminas.Logica;

public class Servicio {


    private static Servicio servicio;
    
    private  Servicio() {
    }

    public static Servicio getServicio(){

        if(servicio==null){
            servicio=new Servicio();
        }
        return servicio;
    }
}
