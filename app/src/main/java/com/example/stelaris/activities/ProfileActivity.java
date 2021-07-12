package com.example.stelaris.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.BbddManager;
import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.parses.ParseSign;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    //TODO Guardado en la bbdd
    //TODO Favoritos en funcion del spinner(ajustes activity)
    //TODO Mover basplanet a qjustes
    //TODO revisar la circle image
    private EditText username, email;
    private Bitmap image;
    private CircleImageView profileImage;
    ActivityResultLauncher<Intent> imageActivityResultLauncher, photoActivityResultLauncher;
    private Usuario usuario;
    private Spinner basePlanetSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        this.username = findViewById(R.id.txtProfileUserName);
        this.username.setOnFocusChangeListener(listenerUsername());
        this.username.setText(this.usuario.getUsername());

        this.email = findViewById(R.id.txtProfileEmail);
        this.email.setOnFocusChangeListener(listenerEmail());
        this.email.setText(this.usuario.getEmail());

        this.profileImage = (CircleImageView) findViewById(R.id.profile_image);
        image = BitmapFactory.decodeByteArray(this.usuario.getPhoto(), 0, this.usuario.getPhoto().length);
        this.profileImage.setImageBitmap(image);

        this.basePlanetSpinner = findViewById(R.id.spBasePlanet);
    }

    public void salvar(View view) throws StringException {

        BbddManager bbddManager = new BbddManager(this, "StelarisDb", null, 1);
        SQLiteDatabase db = bbddManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", this.username.getText().toString());
        values.put("email", this.email.getText().toString());
        values.put("planet", ((BasePlanet)this.basePlanetSpinner.getSelectedItem()).toString());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        values.put("photo", outputStream.toByteArray());

        //db.update("Usuarios", values, );


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
                        username.setText(usuario.getUsername());
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
                        email.setText(usuario.getEmail());
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
                                image = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), imageUri);
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
                            image = (Bitmap) bundel.get("data");
                        }
                    }
                });
    }
}