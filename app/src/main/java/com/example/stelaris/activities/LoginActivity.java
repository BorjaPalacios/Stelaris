package com.example.stelaris.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.BbddManager;
import com.example.stelaris.bbdd.Usuarios;
import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.utils.Security;
import com.example.stelaris.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    //TODO Facebook
    private EditText username, password;
    private LinearLayout layout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BbddManager bbddManager = new BbddManager(this, "StelarisDb", null, 1);

        this.username = findViewById(R.id.txtuserName);
        this.password = findViewById(R.id.txtPassword);
        this.layout = findViewById(R.id.loginLayout);
    }

    public void sigin(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    public void login(View view) {

        String url = "https://stelariswebapi.azurewebsites.net/usuario";
        if(getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
        Snackbar.make(view,getString(R.string.logging), Snackbar.LENGTH_INDEFINITE).show();
        new getUsuarios().execute(url);

    }

    @SuppressLint("StaticFieldLeak")
    private class getUsuarios extends AsyncTask<String, Void, String> {

        private boolean encontrado = false;
        private int idUsuario = 0;
        private BasePlanet location;

        @Override
        protected String doInBackground(String... urls) {
            return Utils.recuperarContenido(urls[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Usuario> lista = Usuarios.convertirJsonUsuarios(jsonarray);

                for (Usuario u : lista) {
                    if (u.getUsername().equalsIgnoreCase(username.getText().toString())) {
                        if (Security.desencriptar(u.getPassword()).equals(password.getText().toString())) {
                            encontrado = true;
                            idUsuario = u.getId();
                            location = u.getPlanet();
                        }
                    }
                }

                if (encontrado) {
                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    i.putExtra("idUsuario", idUsuario);
                    i.putExtra("location", location);
                    startActivity(i);
                } else {
                    Snackbar.make(layout, getString(R.string.userNotFound), Snackbar.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}