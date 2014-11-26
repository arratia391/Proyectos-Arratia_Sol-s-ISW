package com.example.manuel.bahamut;

/**
 * Created by Manuel on 23/11/2014.
 */
public class Tiempo {

    private int hora;
    private int minuto;

    public Tiempo(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public String getTiempo(){
        String tiempo;
        if(minuto<10) {
            if (hora < 10)
                tiempo = "0"+String.valueOf(hora) + ":0" + String.valueOf(minuto);
            tiempo = String.valueOf(hora)+":0"+String.valueOf(minuto);
        }
        else {
            if (hora < 10)
                tiempo = "0"+String.valueOf(hora) + ":" + String.valueOf(minuto);
            tiempo = String.valueOf(hora) + ":" + String.valueOf(minuto);
        }

        return tiempo;
    }
}
