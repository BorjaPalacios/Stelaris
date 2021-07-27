package com.example.stelaris.dialogos;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.example.stelaris.activities.HomeActivity;
import com.example.stelaris.bbdd.BbddManager;
import com.example.stelaris.clases.Luna;
import com.example.stelaris.clases.Planeta;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class DialogoHijos extends DialogFragment {

    private SQLiteDatabase db = null;
    private String nombre;
    private boolean planeta;

    public DialogoHijos(String nombre, boolean planeta) {
        super();
        this.nombre = nombre;
        this.planeta = planeta;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BbddManager bbddManager = new BbddManager(getContext(), "Planetas", null, 1);
        db = bbddManager.getReadableDatabase();
        if (planeta) {
            List<Luna> lunas = new ArrayList<>();

            String[] args = new String[]{this.nombre};

            Cursor c = this.db.rawQuery("SELECT nombre,padre FROM Lunas WHERE padre=?", args);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String nombre = c.getString(0);
                String padre = c.getString(1);

                Luna luna = new Luna(nombre, padre);
                lunas.add(luna);
                c.moveToNext();
            }

            String[] nombres = new String[lunas.size()];
            for (int i = 0; i < lunas.size(); i++)
                nombres[i] = lunas.get(i).getNombre();

            return new AlertDialog.Builder(getActivity()).setTitle("Satelites naturales")
                    .setItems(nombres, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String dato = nombres[which];
                            Luna lunaFind = null;
                            for (Luna luna : lunas) {
                                if (luna.getNombre().equals(dato)) {
                                    lunaFind = luna;
                                    break;
                                }
                            }
                            ((HomeActivity) getActivity()).capturarHijos(lunaFind);
                        }
                    }).create();
        } else {
            List<Planeta> planetas = new ArrayList<>();

            String[] args = new String[] {this.nombre};

            Cursor c = this.db.rawQuery("SELECT nombre,padre,hijos,descripcion FROM Planetas WHERE padre=?", args);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String nombre = c.getString(0);
                String padre = c.getString(1);
                boolean hijos = c.getInt(2) == 1;
                String descripcion = c.getString(3);

                Planeta planeta = new Planeta(nombre, padre, hijos, descripcion);
                planetas.add(planeta);
                c.moveToNext();
            }

            String[] nombres = new String[planetas.size()];
            for (int i = 0; i < planetas.size(); i++)
                nombres[i] = planetas.get(i).getNombre();

            return new AlertDialog.Builder(getActivity()).setTitle("Planetas")
                    .setItems(nombres, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String dato = nombres[which];
                            Planeta planetaFind = null;
                            for(Planeta planeta : planetas){
                                if(planeta.getNombre().equals(dato)){
                                    planetaFind = planeta;
                                    break;
                                }
                            }
                            ((HomeActivity)getActivity()).capturarHijos(planetaFind);
                        }
                    }).create();
        }
    }
}
