package com.example.stelaris.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stelaris.R;
import com.google.android.material.snackbar.Snackbar;

import exceptions.StringException;
import parses.ParseSign;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.username = findViewById(R.id.txtUsernameSign);
        this.username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        ParseSign.parseUserName( v.getContext(), username.getText().toString());
                    } catch (StringException e){
                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        username.setText("");
                    }
                }
            }
        });
        this.email = findViewById(R.id.txtEmailSign);
        this.email = findViewById(R.id.txtPasswordSign);
    }
}