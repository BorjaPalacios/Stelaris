package com.example.stelaris.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.stelaris.R;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.parses.ParseSign;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    //TODO Favoritos en funcion del spinner(ajustes activity)
    //TODO Mover basplanet a qjustes
    private EditText username, email;
    private byte[] image;
    private CircleImageView profileImage;
    ActivityResultLauncher<Intent> imageActivityResultLauncherP, photoActivityResultLauncherP;
    private Usuario usuario;

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

        this.profileImage = findViewById(R.id.profile_image);
        this.profileImage.setImageBitmap(BitmapFactory.decodeByteArray(this.usuario.getPhoto(), 0,
                this.usuario.getPhoto().length));

        imageActivityResultLauncherP = imageHelper();
        photoActivityResultLauncherP = photoHelper();
    }

    public void salvar(View view) throws StringException {
        String url = "https://stelariswebapi.azurewebsites.net/usuario/" + usuario.getId();
        new putUsuario().execute(url);
        Snackbar.make(view, getString(R.string.guardando), Snackbar.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 4000);
    }

    private class putUsuario extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPut httpPut = new HttpPut(urls[0]);

                List<NameValuePair> nameValuePairs = new ArrayList<>(3);
                nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));

                String encodedphoto = java.util.Base64.getEncoder().encodeToString(image);
                nameValuePairs.add(new BasicNameValuePair("photo", encodedphoto));

                httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httpPut);

                return "";
            } catch (Exception ignored) {
                return "error";
            }
        }
    }

    public void getImageP(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imageActivityResultLauncherP.launch(i);
    }

    public void getPhotoP(View view) throws InterruptedException {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoActivityResultLauncherP.launch(i);
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
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), imageUri);
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