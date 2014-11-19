package com.manuel.thot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manuel on 07/11/2014.
 */
public class DataHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "Organizador.sqlite";
    private static final int DB_SCHEME_VERSION = 21;

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdministradorDB.CREATE_TABLE_SEMESTRE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        if(versionAntigua<versionNueva) {
            db.execSQL("DROP TABLE semestre");
            db.execSQL("CREATE TABLE semestre (id integer primary key, nombre text not null, fechadesde text, fechahasta text);");
        }
    }
}
