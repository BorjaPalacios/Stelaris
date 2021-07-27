package com.example.stelaris.bbdd;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stelaris.R;

public class BbddManager extends SQLiteOpenHelper {
    //TODO Falta el cinturon
    //TODO descripcion de las lunas, nombre en ingles
    String sqlPlanetas = "CREATE TABLE IF NOT EXISTS Planetas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL," +
            " hijos INTEGER NOT NULL," +
            " descripcion TEXT )";

    String sqlLunas = "CREATE TABLE IF NOT EXISTS Lunas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL)";

    String sqlEstrellas = "CREATE TABLE IF NOT EXISTS Estrellas (" +
            " nombre TEXT PRIMARY KEY," +
            " descripcion TEXT NOT NULL," +
            " hijos INTEGER NOT NULL)";

    Context context;

    public BbddManager(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
        this.context = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlPlanetas);
        db.execSQL(sqlLunas);
        db.execSQL(sqlEstrellas);

        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Mercury', 'Sun', 0,'" + context.getString(R.string.mercurioD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Venus', 'Sun', 0,'" + context.getString(R.string.venusD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Earth', 'Sun', 1,'" + context.getString(R.string.tierraD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Mars', 'Sun', 1,'" + context.getString(R.string.marteD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('The_Belt', 'Sun', 1,'" + context.getString(R.string.cinturonD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Jupiter', 'Sun', 1,'" + context.getString(R.string.jupiterD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Saturn', 'Sun', 1,'" + context.getString(R.string.saturnoD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Uranus', 'Sun', 1,'" + context.getString(R.string.uranoD) +"')");
        db.execSQL("INSERT INTO Planetas (nombre, padre, hijos, descripcion) VALUES ('Neptune', 'Sun', 1,'" + context.getString(R.string.neptunoD) +"')");

        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Moon', 'Earth')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Fobos', 'Mars')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Deimos', 'Mars')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Io', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Europa', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Ganimedes', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Calisto', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Metis', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Adrastea', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Amaltea', 'Jupiter')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Titan', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Mimas', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Encelado', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Tetis', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Dione', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Rea', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Hiperion', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Japeto', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Febe', 'Saturn')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Miranda', 'Uranus')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Ariel', 'Uranus')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Umbriel', 'Uranus')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Titania', 'Uranus')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Oberon', 'Uranus')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Triton', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Nereida', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Nayade', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Talasa', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Despina', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Galatea', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Larisa', 'Neptune')");
        db.execSQL("INSERT INTO Lunas (nombre, padre) VALUES ('Proteo', 'Neptune')");

        db.execSQL("INSERT INTO Estrellas (nombre, descripcion, hijos) VALUES ('Sun', '" + context.getString(R.string.solD) + "', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        db.execSQL(sqlPlanetas);
        db.execSQL(sqlLunas);
    }
}
