package com.example.stelaris.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.stelaris.R;
import com.example.stelaris.bbdd.BbddManager;
import com.example.stelaris.bbdd.Usuarios;
import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Estrella;
import com.example.stelaris.clases.Luna;
import com.example.stelaris.clases.Planeta;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.clases.carousel.CarouselAdapter;
import com.example.stelaris.clases.carousel.CarouselItem;
import com.example.stelaris.dialogos.DialogoHijos;
import com.example.stelaris.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    //TODO crear activity de ajustes?
    //TODO subasta?
    //TODO chat?
    private TextView descrpicion, planeta;
    private List<CarouselItem> listItems;
    private ViewPager page;
    private TabLayout tabLayout;
    private ImageButton btnHijos, btnPadre, btnMenu;
    private LinearLayout layout;
    private Usuario usuario;
    private String location;
    private int idUsuario;
    private BasePlanet planet;
    private SQLiteDatabase db = null;
    private Planeta bdPlaneta;
    private Luna bdLuna;
    private Estrella bdEstrella;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.descrpicion = findViewById(R.id.lblDescripcion);
        this.btnMenu = findViewById(R.id.btnMenu);
        this.layout = findViewById(R.id.home);
        this.page = findViewById(R.id.my_pager);
        this.tabLayout = findViewById(R.id.my_tablayout);
        this.planeta = findViewById(R.id.lblNombrePlaneta);
        this.btnHijos = findViewById(R.id.btnHijos);
        this.btnPadre = findViewById(R.id.btnPadre);

        idUsuario = getIntent().getExtras().getInt("idUsuario");
        planet = (BasePlanet) getIntent().getExtras().getSerializable("location");

        if (planet.equals(BasePlanet.tierra))
            planeta.setText("Earth");

        if (planet == null)
            location = BasePlanet.baseplanetToString(usuario.getPlanet());
        else {
            if (planet.equals(BasePlanet.tierra))
                location = "earth";
        }

        buscarPlaneta(planeta.getText().toString());

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
    }

    private void imagenNasa(String location) {
        try {
            String url;
            if (location.equalsIgnoreCase("mars"))
                url = "https://images-api.nasa.gov/search?q=" + location + "&media_type=image&keywords=planet,space";
            else
                url = "https://images-api.nasa.gov/search?q=" + location + "&media_type=image&keywords=" + location;
            new Nasa().execute(url);
        } catch (Exception ignored) {

        }
    }

    private class Nasa extends AsyncTask<String, Void, String> {

        List<CarouselItem> carouselItemList = new ArrayList<>();

        @Override
        protected String doInBackground(String... urls) {
            return Utils.recuperarContenido(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    List<String> images = com.example.stelaris.bbdd.Nasa.convertirJsonNasa(jsonObject);
                        for (String image : images) {
                            carouselItemList.add(new CarouselItem(image));
                        }
                    CarouselAdapter carouselAdapter = new CarouselAdapter(page.getContext(), carouselItemList);
                    page.setAdapter(carouselAdapter);
                    tabLayout.setupWithViewPager(page, true);
                    // The_slide_timer
                    if (first == true) {
                        Timer timer = new java.util.Timer();
                        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 5000);
                        first = false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public class SliderTimer extends TimerTask {

            @Override
            public void run() {

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (page.getCurrentItem() < carouselItemList.size() - 1) {
                            page.setCurrentItem(page.getCurrentItem() + 1);
                        } else
                            page.setCurrentItem(0);
                    }
                });
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

    private void buscarPlaneta(String planetSearch) {
        BbddManager bbddManager = new BbddManager(this, "Planetas", null, 1);
        db = bbddManager.getReadableDatabase();

        String[] args = new String[]{planetSearch};

        Cursor c = this.db.rawQuery("SELECT nombre,padre,hijos,descripcion FROM Planetas WHERE nombre=?", args);
        c.moveToFirst();
        String nombre = c.getString(0);
        String padre = c.getString(1);
        boolean hijos = c.getInt(2) == 1;
        String descripcion = c.getString(3);

        this.bdPlaneta = new Planeta(nombre, padre, hijos, descripcion);
        this.bdLuna = null;
        this.bdEstrella = null;
        setPlaneta(bdPlaneta);

        imagenNasa(bdPlaneta.getNombre());
    }

    private void setPlaneta(Planeta planeta1) {

        descrpicion.setText(bdPlaneta.getDescrpcion());
        planeta.setText(bdPlaneta.getNombre());

        if (!planeta1.isHijos()) {
            this.btnHijos.setClickable(false);
            this.btnHijos.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            this.btnHijos.setClickable(true);
            this.btnHijos.setBackgroundColor(Color.parseColor("#41DBEF"));
        }
    }

    public void irHijos(View view) {
        DialogoHijos dialogoHijos;
        if (bdEstrella == null)
            dialogoHijos = new DialogoHijos(bdPlaneta.getNombre(), true);
        else
            dialogoHijos = new DialogoHijos(bdEstrella.getNombre(), false);
        dialogoHijos.show(getFragmentManager(), "dialogo hijos");
    }

    public void capturarHijos(Luna luna) {
        bdLuna = luna;

        if (luna.getNombre().equalsIgnoreCase("moon"))
            imagenNasa("earth-" + luna.getNombre());
        else
            imagenNasa(luna.getNombre());
        this.planeta.setText(luna.getNombre());
        this.descrpicion.setText(luna.getDescripcion());

        this.btnHijos.setClickable(false);
        this.btnHijos.setBackgroundColor(Color.parseColor("#EEEEEE"));

        bdPlaneta = null;
        bdEstrella = null;
    }

    public void capturarHijos(Planeta planeta) {
        bdPlaneta = planeta;

        imagenNasa(planeta.getNombre());
        this.planeta.setText(planeta.getNombre());
        this.descrpicion.setText(planeta.getDescrpcion());

        this.btnHijos.setClickable(true);
        this.btnHijos.setBackgroundColor(Color.parseColor("#41DBEF"));
        this.btnPadre.setClickable(true);
        this.btnPadre.setBackgroundColor(Color.parseColor("#41DBEF"));

        bdLuna = null;
        bdEstrella = null;
    }

    public void irPadre(View view) {
        if (bdPlaneta == null && bdLuna != null) {
            buscarPlaneta(bdLuna.getPadre());
        } else if (bdPlaneta != null && bdLuna == null) {
            buscarEstrella(bdPlaneta.getPadre());
        }
    }

    public void buscarEstrella(String nombre) {
        BbddManager bbddManager = new BbddManager(this, "Planetas", null, 1);
        db = bbddManager.getReadableDatabase();

        String[] args = new String[]{nombre};

        Cursor c = this.db.rawQuery("SELECT nombre,descripcion,hijos FROM Estrellas WHERE nombre=?", args);
        c.moveToFirst();
        String nombreE = c.getString(0);
        String descripcion = c.getString(1);
        boolean hijos = c.getInt(2) == 1;

        this.bdPlaneta = null;
        this.bdLuna = null;
        this.bdEstrella = new Estrella(nombreE, descripcion, hijos);

        this.planeta.setText(bdEstrella.getNombre());
        this.descrpicion.setText(bdEstrella.getDescripcion());

        this.btnPadre.setClickable(false);
        this.btnPadre.setBackgroundColor(Color.parseColor("#EEEEEE"));

        imagenNasa(bdEstrella.getNombre());
    }
}