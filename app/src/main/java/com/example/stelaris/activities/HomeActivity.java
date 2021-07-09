package com.example.stelaris.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;
import com.example.stelaris.clases.Usuario;

public class HomeActivity extends AppCompatActivity {
    //TODO Crear los botones para desplazarnos
    //TODO Conexion con la api de la nasa
    //TODO Foto de default
    private TextView celeste, descrpicion;
    private ImageView imagenNasa;
    private Button btnMenu;
    private LinearLayout layout;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.celeste = findViewById(R.id.lblCeleste);
        this.descrpicion = findViewById(R.id.lblDescripcion);
        this.imagenNasa = findViewById(R.id.imgNasa);
        this.btnMenu = findViewById(R.id.btnMenu);
        this.layout = (LinearLayout) findViewById(R.id.home);

        this.usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        registerForContextMenu(btnMenu);
        this.btnMenu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                v.showContextMenu(1000, 0);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuPerfil:
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("Usuario", usuario);
                startActivity(i);
                break;
            case R.id.menuOpciones:
                Toast toast = Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.menuCerrarSesion:
                finish();
                break;
        }
        return true;
    }
}