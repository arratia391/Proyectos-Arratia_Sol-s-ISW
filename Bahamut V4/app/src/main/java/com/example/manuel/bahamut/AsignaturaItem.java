package com.example.manuel.bahamut;

/**
 * Created by Manuel on 21/11/2014.
 */
public class AsignaturaItem {

    private String nombre;
    private int color;
    private int icono;

    public AsignaturaItem(String nombre, int color, int icono){
        this.nombre=nombre;
        this.color=color;
        this.icono=icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
