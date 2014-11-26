package com.example.manuel.bahamut;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Manuel on 07/11/2014.
 */
public class DataHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "Organizador.sqlite";
    private static final int DB_SCHEME_VERSION = 31;

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdministradorDB.CREATE_TABLE_SEMESTRE);
        db.execSQL(AdministradorDB.CREATE_TABLE_ASIGNATURA);
        db.execSQL(AdministradorDB.CREATE_TABLE_GESTION_SA);
        db.execSQL(AdministradorDB.CREATE_TABLE_HORARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        Log.i("Funciona","Funciona");
        db.execSQL("DROP TABLE clase");
        db.execSQL("DROP TABLE gestion_sa");
        db.execSQL("DROP TABLE semestre");
        db.execSQL("DROP TABLE asignatura");

        onCreate(db);
    }
}
