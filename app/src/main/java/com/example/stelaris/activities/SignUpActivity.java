package com.example.stelaris.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.Usuarios;
import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.exceptions.StringException;
import com.example.stelaris.exceptions.UsuarioException;
import com.example.stelaris.parses.ParseSign;
import com.example.stelaris.utils.Security;
import com.example.stelaris.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, password, email;
    private CircleImageView imageView;
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

        this.imageView = findViewById(R.id.register_image);
        this.imageView.setImageResource(R.drawable.fotodefault);

        imageActivityResultLauncher = imageHelper();
        photoActivityResultLauncher = photoHelper();
    }

    public void getImage(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imageActivityResultLauncher.launch(i);
    }

    public void getPhoto(View view) throws InterruptedException {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, 1);
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoActivityResultLauncher.launch(i);
        }
    }

    public void registrar(View view) {
        try {
            String url = "https://stelariswebapi.azurewebsites.net/usuario";

            if (ParseSign.parseUserName(this, this.username.getText().toString()) &&
                    ParseSign.parsePassword(this, this.password.getText().toString()) &&
                    ParseSign.parseEmail(this, this.email.getText().toString())) {

                getCurrentFocus().clearFocus();
                Snackbar.make(layout, getString(R.string.paciencia), Snackbar.LENGTH_INDEFINITE).show();
                new getUsuarios().execute(url);
            }
        } catch (StringException e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getUsuarios extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return Utils.recuperarContenido(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Usuario> lista = Usuarios.convertirJsonUsuarios(jsonarray);

                for (Usuario u : lista) {
                    if (u.getUsername().equalsIgnoreCase(username.getText().toString()))
                        throw new UsuarioException(getString(R.string.userNameExists), "username");
                    if (u.getEmail().equalsIgnoreCase(email.getText().toString()))
                        throw new UsuarioException(getString(R.string.emailExists), "email");
                }

                String url = "https://stelariswebapi.azurewebsites.net/usuario";

                new postUsuario().execute(url);

            } catch (UsuarioException e) {
                if (e.camp.equalsIgnoreCase("username")) {
                    username.setText("");
                } else if (e.camp.equalsIgnoreCase("email")) {
                    email.setText("");
                }
                Snackbar.make(layout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            } catch (JSONException ignored) {

            }
        }
    }

    private class postUsuario extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... urls) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urls[0]);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>(5);
                nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", Security.encriptar(password.getText().toString())));

                if (image == null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fotodefault);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    image = outputStream.toByteArray();
                }
                String encodedphoto = java.util.Base64.getEncoder().encodeToString(image);
                nameValuePairs.add(new BasicNameValuePair("photo", encodedphoto));

                nameValuePairs.add(new BasicNameValuePair("planet", "tierra"));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                return Utils.convertirInputToString(response.getEntity().getContent());
            } catch (IOException e) {
                return "error";
            } catch (Exception e) {
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar.make(layout, getString(R.string.userCreate), Snackbar.LENGTH_SHORT).show();


            Intent i = new Intent(getBaseContext(), HomeActivity.class);
            i.putExtra("idUsuario", Integer.parseInt(result));
            i.putExtra("location", BasePlanet.tierra);
            startActivity(i);
            finish();
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
                                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,
                                        image.length));
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
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,
                                    image.length));
                        }
                    }
                });
    }
}