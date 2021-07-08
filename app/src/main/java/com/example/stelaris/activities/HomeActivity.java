package com.example.stelaris.activities;

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

public class HomeActivity extends AppCompatActivity {

    private TextView celeste, descrpicion;
    private ImageView imagenNasa;
    private Button btnMenu;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.celeste = findViewById(R.id.lblCeleste);
        this.descrpicion = findViewById(R.id.lblDescripcion);
        this.imagenNasa = findViewById(R.id.imgNasa);
        this.btnMenu = findViewById(R.id.btnMenu);
        this.layout = (LinearLayout) findViewById(R.id.home);

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
        Toast toast = null;

        switch (item.getItemId()) {
            case R.id.menuPerfil:
                toast = Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT);
                this.layout.setAlpha(0.2f);
                break;
            case R.id.menuOpciones:
                toast = Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT);
                break;
            case R.id.menuCerrarSesion:
                finish();
                break;
        }

        toast.show();

        return true;
    }
}