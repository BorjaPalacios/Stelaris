package com.example.stelaris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.BbddManager;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BbddManager bbddManager = new BbddManager(this, "StelarisDb", null, 1);

        this.username = findViewById(R.id.txtuserName);
        this.password = findViewById(R.id.txtPassword);
    }

    public void sigin(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}