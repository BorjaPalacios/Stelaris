package com.example.stelaris.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import com.example.stelaris.R;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.parses.ParseSign;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    //TODO Guardado en la bbdd
    //TODO Favoritos en funcion del spinner
    private EditText username, email;
    private Bitmap image;
    ActivityResultLauncher<Intent> imageActivityResultLauncher, photoActivityResultLauncher;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.username = findViewById(R.id.txtProfileUserName);
        this.email = findViewById(R.id.txtProfileEmail);

        this.usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
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