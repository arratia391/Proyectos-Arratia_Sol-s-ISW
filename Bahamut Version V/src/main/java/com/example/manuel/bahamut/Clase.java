package com.example.manuel.bahamut;

/**
 * Created by Manuel on 25/11/2014.
 */
public class Clase {

    private int id;
    private String tipo;
    private String sala;
    private String FechaDesde;
    private String FechaHasta;
    private String TiempoDesde;
    private String TiempoHasta;
    private String semana;

    public Clase(int id,String tipo, String sala, String fechaDesde, String fechaHasta, String tiempoDesde, String tiempoHasta, String semana) {
        this.id=id;
        this.tipo = tipo;
        this.sala = sala;
        FechaDesde = fechaDesde;
        FechaHasta = fechaHasta;
        TiempoDesde = tiempoDesde;
        TiempoHasta = tiempoHasta;
        this.semana = semana;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getFechaDesde() {
        return FechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        FechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return FechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        FechaHasta = fechaHasta;
    }

    public String getTiempoDesde() {
        return TiempoDesde;
    }

    public void setTiempoDesde(String tiempoDesde) {
        TiempoDesde = tiempoDesde;
    }

    public String getTiempoHasta() {
        return TiempoHasta;
    }

    public void setTiempoHasta(String tiempoHasta) {
        TiempoHasta = tiempoHasta;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

