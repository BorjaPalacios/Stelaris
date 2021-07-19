package com.example.stelaris.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.BbddManager;
import com.example.stelaris.bbdd.Usuarios;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.exceptions.UsuarioException;
import com.example.stelaris.parses.ParseSign;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    //TODO Registro en bbdd, comprobacion de q no existe
    //TODO Necesidad de boton facebook?
    private EditText username, password, email;
    private byte[] image;
    private LinearLayout layout;
    ActivityResultLauncher<Intent> imageActivityResultLauncher, photoActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.layout = findViewById(R.id.linearLayout);

        this.username = findViewById(R.id.txtUsernameSign);
        this.username.setOnFocusChangeListener(listenerUsername());

        this.email = findViewById(R.id.txtEmailSign);
        this.email.setOnFocusChangeListener(listenerEmail());

        this.password = findViewById(R.id.txtPasswordSign);
        this.password.setOnFocusChangeListener(listenerPassword());

        imageActivityResultLauncher = imageHelper();
        photoActivityResultLauncher = photoHelper();
    }

    public void getImage(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imageActivityResultLauncher.launch(i);
    }

    public void getPhoto(View view) {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoActivityResultLauncher.launch(i);

    }

    public void registrar(View view) {
        try {
            String url = "https://stelariswebapi.azurewebsites.net/usuario";
            new getUsuarios().execute(url);
        } catch (Exception ignored) {

        }
    }

    private class getUsuarios extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return Usuarios.recuperarContenido(urls[0]);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Datos recibidos!", Toast.LENGTH_LONG).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Usuario> lista = Usuarios.convertirJsonUsuario(jsonarray);

                for (Usuario u : lista) {
                    if (u.getUsername().equalsIgnoreCase(username.getText().toString()))
                        throw new UsuarioException(getString(R.string.userNameExists), "username");
                    if (u.getEmail().equalsIgnoreCase(email.getText().toString()))
                        throw new UsuarioException(getString(R.string.emailExists), "email");
                }

            } catch (UsuarioException e) {
                if(e.camp.equalsIgnoreCase("username")){
                    username.setText("");
                } else if(e.camp.equalsIgnoreCase("email")){
                    email.setText("");
                }
                Snackbar.make(layout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            } catch (JSONException ignored) {

            }
        }
    }

    public void registrar(View view) {
        try {
            BbddManager bbddManager = new BbddManager(this, "StelarisDb", null, 1);
            SQLiteDatabase db = bbddManager.getWritableDatabase();

            if (ParseSign.parseUserName(this, this.username.getText().toString()) &&
                    ParseSign.parsePassword(this, this.password.getText().toString()) &&
                    ParseSign.parseEmail(this, this.email.getText().toString())) {


                Usuario usuario = new Usuario(this.username.getText().toString(), this.password.getText().toString(),
                        this.email.getText().toString(), image);

                ContentValues values = new ContentValues();
                values.put("nombre", usuario.getUsername());
                values.put("email", usuario.getEmail());

                values.put("photo", usuario.getPhoto());
                values.put("planet", usuario.getPlanet());

                db.insert("Usuarios", null, values);

                Intent i = new Intent(this, HomeActivity.class);
                i.putExtra("Usuario", usuario);
                startActivity(i);
            }
        } catch (StringException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private View.OnFocusChangeListener listenerUsername() {

        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        ParseSign.parseUserName(v.getContext(), username.getText().toString());
                    } catch (StringException e) {
                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        username.setText("");
                    }
                }
            }
        };
    }

    private View.OnFocusChangeListener listenerEmail() {

        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        ParseSign.parseEmail(v.getContext(), email.getText().toString());
                    } catch (StringException e) {
                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        email.setText("");
                    }
                }
            }
        };
    }

    private View.OnFocusChangeListener listenerPassword() {

        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        ParseSign.parsePassword(v.getContext(), password.getText().toString());
                    } catch (StringException e) {
                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        password.setText("");
                    }
                }
            }
        };
    }

    public ActivityResultLauncher<Intent> imageHelper() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(), imageUri);

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                image = outputStream.toByteArray();
                            } catch (IOException e) {

                            }
                        }
                    }
                });
    }

    public ActivityResultLauncher<Intent> photoHelper() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            Bundle bundel = data.getExtras();
                            Bitmap bitmap = (Bitmap) bundel.get("data");

                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            image = outputStream.toByteArray();
                        }
                    }
                });
    }
}