package com.example.stelaris.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BbddManager extends SQLiteOpenHelper {
    //TODO Crear todo el sistema de sqlite
    String sqlCreate = "CREATE TABLE IF NOT EXISTS Usuarios (usuario_id INTEGER PRIMARY KEY," +
            " nombre TEXT NOT NULL," +
            " email TEXT NOT NULL," +
            " photo BLOB NOT NULL)";

    public BbddManager(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        db.execSQL(sqlCreate);
    }
}
