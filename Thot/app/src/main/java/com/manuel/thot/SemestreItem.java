package com.manuel.thot;

/**
 * Created by Manuel on 08/11/2014.
 */
public class SemestreItem {

    private String nombre;
    private String fecha_desde;
    private String fecha_hasta;

    public String getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(String fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public String getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(String fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public SemestreItem(){}

    public SemestreItem(String nombre, String fecha_desde, String fecha_hasta){
        this.nombre = nombre;
        this.fecha_desde = fecha_desde;
        this.fecha_hasta= fecha_hasta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
