package com.example.manuel.bahamut;

/**
 * Created by Manuel on 25/11/2014.
 */
public class Hoy {

    private String nombreAsignatura;
    private String TipoAsignatura;
    private String PeriodoDesde;
    private String PeriodoHasta;
    private String sala;
    private int icon;
    private int color;

    public Hoy(String nombreAsignatura, String tipoAsignatura, String periodoDesde, String periodoHasta, String sala, int icon, int color) {
        this.nombreAsignatura = nombreAsignatura;
        TipoAsignatura = tipoAsignatura;
        PeriodoDesde = periodoDesde;
        PeriodoHasta = periodoHasta;
        this.sala = sala;
        this.icon = icon;
        this.color = color;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getTipoAsignatura() {
        return TipoAsignatura;
    }

    public void setTipoAsignatura(String tipoAsignatura) {
        TipoAsignatura = tipoAsignatura;
    }

    public String getPeriodoDesde() {
        return PeriodoDesde;
    }

    public void setPeriodoDesde(String periodoDesde) {
        PeriodoDesde = periodoDesde;
    }

    public String getPeriodoHasta() {
        return PeriodoHasta;
    }

    public void setPeriodoHasta(String periodoHasta) {
        PeriodoHasta = periodoHasta;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
