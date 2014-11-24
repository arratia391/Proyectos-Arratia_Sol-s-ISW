package com.example.manuel.bahamut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String CREATE_TABLE_SEMESTRE = "create table semestre (id integer primary key autoincrement, nombre text not null, fechadesde text, fechahasta text);";

    public static final String CREATE_TABLE_ASIGNATURA = "create table asignatura (id integer primary key autoincrement, nombre text not null, color integer, icono integer);";

    public static final String CREATE_TABLE_GESTION_SA = "create table gestion_sa (id_gestionSemestre integer not null, "+
                                                         "id_gestionAsignatura integer not null, "+
                                                         "foreign key(id_gestionSemestre) references semestre(id) ON DELETE CASCADE, "+
                                                         "foreign key(id_gestionAsignatura) references asignatura(id) ON DELETE CASCADE);";

    public static final String CREATE_TABLE_HORARIO = "create table clase (id_clase integer primary key autoincrement, CfechaDesde text, CfechaHasta text, "+
                                                                                "CTiempoDesde text, CtiempoHasta text, Sala text, Semana text, asignaturaFK integer not null, "+
                                                                                  "foreign key(asignaturaFK) references asignatura(id) ON DELETE CASCADE);";


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

    private ContentValues generarContentValuesAsignatura(String nombre, int color, int icono){
        ContentValues valores=new ContentValues();
        valores.put("nombre",nombre);
        valores.put("color",color);
        valores.put("icono",icono);
        return valores;
    }

    private ContentValues generarContentValuesGestionSA(int id_Semestre, int id_Asignatura){
        ContentValues valores=new ContentValues();
        valores.put("id_gestionSemestre",id_Semestre);
        valores.put("id_gestionAsignatura",id_Asignatura);
        return valores;
    }

    public void insertar(String nombre, String fecha_desde, String fecha_hasta) {
        //bd.insert(TABLA, NullColumnHack, ContentValues);
        db.insert(TABLE_NAME, null, generarContentValues(nombre,fecha_desde,fecha_hasta) );
    }

    public void insertarAsignatura(String nombre, int color, int icono){
        db.insert("asignatura",null,generarContentValuesAsignatura(nombre,color,icono));
    }

    public void insertarGestionSA(int id_Semestre, int id_Asignatura){
        db.insert("gestion_sa",null,generarContentValuesGestionSA(id_Semestre,id_Asignatura));
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

    public Cursor cargarAsignatura(){
        return db.rawQuery("select id,nombre,color,icono from asignatura",null);
    }

    public Cursor cargarIdAsignatura( String nombreAsignatura){
        String[] args = new String[]{nombreAsignatura};
        return db.rawQuery("select id from asignatura where nombre=?",args);
    }

    //Retornara las asignaturas clonadas dentro de un mismo semestre. Si retorna vacio, ninguna se repite.
    public Cursor asignaturEnRespectivoSemestre(int id_semestre, String nombre){

        return db.rawQuery("select x.nombre from asignatura x, gestion_sa y where y.id_gestionSemestre="+id_semestre,null);
    }

    public Cursor contarRepetidos(int id_semestre, String nombre){
        return db.rawQuery("select count(*) from asignatura x, gestion_sa y\n" +
                "where y.id_gestionSemestre="+id_semestre+" and x.id=y.id_gestionAsignatura\n" +
                "and x.nombre='"+nombre+"'",null);
    }

    public Cursor mostrarAsignaturas(int id_semestre){
        return db.rawQuery("select x.nombre, x.color, x.icono from asignatura x, gestion_sa y\n" +
                "where y.id_gestionSemestre="+id_semestre+" and x.id=y.id_gestionAsignatura",null);
    }

    public Cursor mostrarAsignaturaDeUnSemestre(int id_semestre, String nombreAsignatura){
        return db.rawQuery("select id,nombre from asignatura where id in (select id_gestionAsignatura from gestion_sa\n" +
                "where id_gestionSemestre="+id_semestre+" ) and nombre='"+nombreAsignatura+"'",null);
    }


    public void eliminar(String nombre) {
        //bd.delete (Tabla, Claúsula Where, Argumentos Where)
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void eliminarTodoGestion(){
        db.delete("gestion_sa",null,null);
    }

    public void eliminarAsignaturaDeUnSemestre(int id_semestre, String nombreAsignatura){
        db.rawQuery("delete from asignatura where id in (select id_gestionAsignatura from gestion_sa\n" +
                                                        "where id_gestionSemestre="+id_semestre+" ) and nombre='"+nombreAsignatura+"'",null);
    }

    public void eliminarAsignatura(String nombre){
        db.delete("asignatura", "nombre=?",new String[]{nombre});
    }

    public void eliminarAsignatura(int idAsignatura){
        db.delete("asignatura", "id="+idAsignatura,null);
    }

    public void modificarSemestre(String antiguoNombre, String nuevoNombre, String fecha_desde, String fecha_hasta){
        db.update(TABLE_NAME,generarContentValues(nuevoNombre,fecha_desde,fecha_hasta), CN_NAME + "=?", new String[]{antiguoNombre});
    }

    public void Close(){
        db.close();
    }


}
