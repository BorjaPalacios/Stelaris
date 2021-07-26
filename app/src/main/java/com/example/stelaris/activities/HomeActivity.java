package com.example.stelaris.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.Usuarios;
import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.clases.carousel.CarouselAdapter;
import com.example.stelaris.clases.carousel.CarouselItem;
import com.example.stelaris.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //TODO Crear los botones para desplazarnos
    //TODO crear activity de ajustes
    private TextView celeste, descrpicion;
    private ImageView imagenNasa;
    private List<CarouselItem> listItems;
    private ViewPager page;
    private TabLayout tabLayout;
    private Button btnMenu;
    private LinearLayout layout;
    private Usuario usuario;
    private String location;
    private int idUsuario;
    private BasePlanet planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.celeste = findViewById(R.id.lblCeleste);
        this.descrpicion = findViewById(R.id.lblDescripcion);
        //this.imagenNasa = findViewById(R.id.imgNasa);
        this.btnMenu = findViewById(R.id.btnMenu);
        this.layout = findViewById(R.id.home);
        this.page = findViewById(R.id.my_pager) ;
        this.tabLayout = findViewById(R.id.my_tablayout);

        idUsuario = getIntent().getExtras().getInt("idUsuario");
        planet = (BasePlanet) getIntent().getExtras().getSerializable("location");

        conseguirUsuario(getIntent().getExtras().getInt("idUsuario"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        registerForContextMenu(btnMenu);
        this.btnMenu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                v.showContextMenu(1000, 0);
            }
        });

        if (planet == null)
            location = BasePlanet.baseplanetToString(usuario.getPlanet());
        else {
            if (planet.equals(BasePlanet.tierra))
                location = "earth";
        }

        imagenNasa(location);
    }

    private void imagenNasa(String location) {
        try {
            String url = "https://images-api.nasa.gov/search?q=" + location + "&media_type=image&keywords=star,space";
            new Nasa().execute(url);
        } catch (Exception ignored) {

        }
    }

    private class Nasa extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return Utils.recuperarContenido(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    List<String> images = com.example.stelaris.bbdd.Nasa.convertirJsonNasa(jsonObject);
                    List<CarouselItem> carouselItemList = new ArrayList<>();
                    for(String image : images){
                        carouselItemList.add(new CarouselItem(image));
                    }
                    CarouselAdapter carouselAdapter = new CarouselAdapter(page.getContext(), carouselItemList);
                    page.setAdapter(carouselAdapter);
                    tabLayout.setupWithViewPager(page,true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void conseguirUsuario(int userId) {
        try {
            String url = "https://stelariswebapi.azurewebsites.net/usuario/" + userId;
            new getUsuario().execute(url);
        } catch (Exception ignored) {

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getUsuario extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return Utils.recuperarContenido(urls[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    usuario = Usuarios.convertirJsonUsuario(jsonObject);
                }
            } catch (JSONException ignored) {

            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuPerfil:
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("Usuario", usuario);
                startActivity(i);
                conseguirUsuario(usuario.getId());
                break;
            case R.id.menuOpciones:
                Toast toast = Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.menuCerrarSesion:
                finish();
                break;
        }
        return true;
    }
}