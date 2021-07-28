package com.example.stelaris.bbdd;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stelaris.R;

public class BbddManager extends SQLiteOpenHelper {
    String sqlPlanetas = "CREATE TABLE IF NOT EXISTS Planetas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL," +
            " hijos INTEGER NOT NULL," +
            " descripcion TEXT )";

    String sqlLunas = "CREATE TABLE IF NOT EXISTS Lunas (" +
            " nombre TEXT PRIMARY KEY," +
            " padre TEXT NOT NULL," +
            " descripcion TEXT NOT NULL)";

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

        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Moon', 'Earth','" + context.getString(R.string.lunaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Phobos', 'Mars','" + context.getString(R.string.fobosD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Deimos', 'Mars','" + context.getString(R.string.deimosD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Io', 'Jupiter','" + context.getString(R.string.ioD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Europa', 'Jupiter','" + context.getString(R.string.europaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Ganymede', 'Jupiter','" + context.getString(R.string.ganimedesD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Callisto', 'Jupiter','" + context.getString(R.string.calistoD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Metis', 'Jupiter','" + context.getString(R.string.metisD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Adrastea', 'Jupiter','" + context.getString(R.string.adrasteaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Amalthea', 'Jupiter','" + context.getString(R.string.amalteaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Thebe', 'Jupiter','" + context.getString(R.string.tebeD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Titan', 'Saturn','" + context.getString(R.string.titanD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Mimas', 'Saturn','" + context.getString(R.string.mimasD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Enceladus', 'Saturn','" + context.getString(R.string.enceladoD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Tethys', 'Saturn','" + context.getString(R.string.tetisD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Dione', 'Saturn','" + context.getString(R.string.dioneD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Rhea', 'Saturn','" + context.getString(R.string.reaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Hyperion', 'Saturn','" + context.getString(R.string.hiperionD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Iapetus', 'Saturn','" + context.getString(R.string.japetoD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Phoebe', 'Saturn','" + context.getString(R.string.febeD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Miranda', 'Uranus','" + context.getString(R.string.mirandaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Ariel', 'Uranus','" + context.getString(R.string.arielD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Umbriel', 'Uranus','" + context.getString(R.string.umbrielD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Titania', 'Uranus','" + context.getString(R.string.titaniaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Oberon', 'Uranus','" + context.getString(R.string.oberonD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Triton', 'Neptune','" + context.getString(R.string.tritonD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Nereid', 'Neptune','" + context.getString(R.string.nereidaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Naiad', 'Neptune','" + context.getString(R.string.nayadeD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Halimede', 'Neptune','" + context.getString(R.string.halimedeD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Thalassa', 'Neptune','" + context.getString(R.string.talasaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Despina', 'Neptune','" + context.getString(R.string.despinaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Galatea', 'Neptune','" + context.getString(R.string.galateaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Larissa', 'Neptune','" + context.getString(R.string.larisaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Proteus', 'Neptune','" + context.getString(R.string.proteoD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Ceres', 'The_Belt','" + context.getString(R.string.ceresD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Vesta', 'The_Belt','" + context.getString(R.string.vestaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Pallas', 'The_Belt','" + context.getString(R.string.palasD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Hygiea', 'The_Belt','" + context.getString(R.string.hygieaD) +"')");
        db.execSQL("INSERT INTO Lunas (nombre, padre, descripcion) VALUES ('Juno', 'The_Belt','" + context.getString(R.string.junoD) +"')");

        db.execSQL("INSERT INTO Estrellas (nombre, descripcion, hijos) VALUES ('Sun', '" + context.getString(R.string.solD) + "', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        db.execSQL(sqlPlanetas);
        db.execSQL(sqlLunas);
    }
}
