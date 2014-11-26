package com.example.manuel.bahamut;

import android.util.Log;

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
        if(dia<10){
            fecha = "0"+String.valueOf(dia)+" de "+arrayString[mes]+" del "+String.valueOf(año);
        }else {
            fecha = String.valueOf(dia) + " de " + arrayString[mes] + " del " + String.valueOf(año);
        }
        return fecha;
    }

    public static int TransformarFechaInt(String fecha){

        String aa="",dd="",mm="";
        int a1,d1,m1;

        if(fecha.contains("Enero")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "01";
        }
        if(fecha.contains("Febrero")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "02";
        }
        if(fecha.contains("Marzo")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "03";
        }
        if(fecha.contains("Abril")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "04";
        }
        if(fecha.contains("Mayo")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "05";
        }
        if(fecha.contains("Junio")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "06";
        }
        if(fecha.contains("Julio")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "07";
        }
        if(fecha.contains("Agosto")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "08";
        }
        if(fecha.contains("Septiembre")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "09";
        }
        if(fecha.contains("Octubre")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "10";
        }
        if(fecha.contains("Noviembre")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "11";
        }
        if(fecha.contains("Diciembre")){
            dd = fecha.substring(0,2);
            aa = fecha.substring(fecha.length()-4,fecha.length());
            mm = "12";
        }

        a1= Integer.parseInt(aa);
        d1= Integer.parseInt(dd);
        m1= Integer.parseInt(mm);

            int total = a1 * 10000 + m1 * 100 + d1;

        return total;
    }

    public static Boolean fechaActual(int dia, int mes, int año, String fechaDesde, String fechaHasta){

        int FechaDesde= TransformarFechaInt(fechaDesde);
        int FechaHasta= TransformarFechaInt(fechaHasta);
        int FechaComprobar= año*10000+mes*100+dia;

        if(FechaComprobar<=FechaHasta && FechaComprobar>=FechaDesde){
            return true;
        }else{
            return false;
        }
    }

}
