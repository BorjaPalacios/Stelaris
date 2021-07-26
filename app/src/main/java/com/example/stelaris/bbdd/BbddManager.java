package com.example.stelaris.bbdd;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stelaris.R;

public class BbddManager extends SQLiteOpenHelper {
    //TODO Crear todo el sistema de sqlite
    String sqlPlanetas = "CREATE TABLE IF NOT EXISTS Planetas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL," +
            " hijos INTEGER NOT NULL," +
            " descripcion TEXT )";

    String sqlLunas = "CREATE TABLE IF NOT EXISTS Lunas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL)";

    Context context;

    public BbddManager(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
        this.context = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlPlanetas);
        db.execSQL(sqlLunas);

        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Mercury', 'Sun', 0,'" + context.getString(R.string.mercurioD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Venus', 'Sun', 0,'" + context.getString(R.string.venusD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Earth', 'Sun', 1,'" + context.getString(R.string.tierraD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Mars', 'Sun', 1,'" + context.getString(R.string.marteD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('The_Belt', 'Sun', 1,'" + context.getString(R.string.cinturonD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Jupiter', 'Sun', 1,'" + context.getString(R.string.jupiterD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Saturn', 'Sun', 1,'" + context.getString(R.string.saturnoD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Uranus', 'Sun', 1,'" + context.getString(R.string.uranoD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Neptune', 'Sun', 1,'" + context.getString(R.string.neptunoD) +"')");

        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Moon', 'Tierra')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Fobos', 'Marte')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Deimos', 'Marte')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Io', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Europa', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Ganimedes', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Calisto', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Metis', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Adrastea', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Amaltea', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Titan', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Mimas', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Encelado', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Tetis', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Dione', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Rea', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Hiperion', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Japeto', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Febe', 'Saturno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Miranda', 'Urano')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Ariel', 'Urano')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Umbriel', 'Urano')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Titania', 'Urano')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Oberon', 'Urano')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Triton', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Nereida', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Nayade', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Talasa', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Despina', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Galatea', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Larisa', 'Neptuno')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Proteo', 'Neptuno')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        db.execSQL(sqlPlanetas);
        db.execSQL(sqlLunas);
    }
}
