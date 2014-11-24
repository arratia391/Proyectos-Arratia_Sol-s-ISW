package com.example.manuel.bahamut;

/**
 * Created by Lara on 13-11-2014.
 */


public class Fecha {

    private static final String[] arrayString = new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    private int dia;
    private int mes;
    private int año;

    private String fecha;

    public Fecha(int dia, int mes, int año){
        this.dia=dia;
        this.mes=mes;
        this.año=año;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String listar(){
        return dia + "/" + mes + "/" + año;
    }

    public String getFecha(){
        fecha = String.valueOf(dia)+" de "+arrayString[mes]+" del "+String.valueOf(año);
        return fecha;
    }

}
