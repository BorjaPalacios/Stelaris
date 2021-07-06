package com.example.stelaris.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;

public class HomeActivity extends AppCompatActivity {

    private TextView celeste, descrpicion;
    private ImageView imagenNasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.celeste = findViewById(R.id.lblCeleste);
        this.descrpicion = findViewById(R.id.lblDescripcion);
        this.imagenNasa = findViewById(R.id.imgNasa);
    }
}