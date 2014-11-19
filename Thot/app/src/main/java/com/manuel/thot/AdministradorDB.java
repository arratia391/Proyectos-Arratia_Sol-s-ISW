package com.manuel.thot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

/**
 * Created by Manuel on 07/11/2014.
 */
public class AdministradorDB {

    private DataHelper helper;
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "semestre";

    public static final String CN_ID = "_id";
    public static final String CN_NAME = "nombre";
    public static final String CN_FECHADESDE= "fechadesde";
    public static final String CN_FECHAHASTA= "fechahasta";

    //Crea la tabla semestre.
    public static final String CREATE_TABLE_SEMESTRE = "create table " +TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null," + CN_FECHADESDE+ "text," + CN_FECHAHASTA + "text);" ;




    public AdministradorDB(Context context) {

        helper = new DataHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generarContentValues(String nombre, String fecha_desde, String fecha_hasta) {
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME, nombre);
        valores.put(CN_FECHADESDE, fecha_desde);
        valores.put(CN_FECHAHASTA, fecha_hasta);
        return valores;
    }

    public void insertar(String nombre, String fecha_desde, String fecha_hasta) {
        //bd.insert(TABLA, NullColumnHack, ContentValues);
        db.insert(TABLE_NAME, null, generarContentValues(nombre,fecha_desde,fecha_hasta) );

    }

    /* No funciono mas.
    public Cursor cargarSemestre() {
        String[] columnas = new String[]{CN_ID, CN_NAME, CN_FECHADESDE,CN_FECHAHASTA};
        return db.query(TABLE_NAME, columnas, null, null, null, null, null);
    }
    */

    public Cursor cargar(){
        return db.rawQuery("SELECT id,nombre,fechadesde,fechahasta from semestre",null);
    }

    public Cursor cargarFecha(String nombre){
        String[] args = new String[]{nombre};
        return db.rawQuery("SELECT fechadesde,fechahasta from semestre where nombre=?",args);
    }


    public void eliminar(String nombre) {
        //bd.delete (Tabla, Claúsula Where, Argumentos Where)
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void modificarSemestre(String antiguoNombre, String nuevoNombre, String fecha_desde, String fecha_hasta){
        db.update(TABLE_NAME,generarContentValues(nuevoNombre,fecha_desde,fecha_hasta), CN_NAME + "=?", new String[]{antiguoNombre});
    }



}
