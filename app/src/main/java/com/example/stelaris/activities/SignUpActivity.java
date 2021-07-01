package com.example.stelaris.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stelaris.R;
import com.example.stelaris.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.parses.ParseSign;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, password, email;
    private ImageView image;
    private final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.username = findViewById(R.id.txtUsernameSign);
        this.username.setOnFocusChangeListener(listenerUsername());

        this.email = findViewById(R.id.txtEmailSign);
        this.email.setOnFocusChangeListener(listenerEmail());

        this.password = findViewById(R.id.txtPasswordSign);
        this.password.setOnFocusChangeListener(listenerPassword());

        this.image = findViewById(R.id.imageView2);
    }

    public void getImage(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            this.image.setImageURI(imageUri);
        }
    }

    public void registrar(View view) {
        try {
            if (ParseSign.parseUserName(this, this.username.getText().toString()) &&
                    ParseSign.parsePassword(this, this.password.getText().toString()) &&
                    ParseSign.parseEmail(this, this.email.getText().toString())) {
                Usuario usuario = new Usuario(this.username.getText().toString(), this.password.getText().toString(),
                        this.email.getText().toString());
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
}