package com.example.stelaris.bbdd;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.stelaris.clases.BasePlanet;
import com.example.stelaris.clases.Usuario;
import com.example.stelaris.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Usuarios {

    public static List<Usuario> convertirJsonUsuarios(JSONArray jsonarray) throws JSONException {

        List<Usuario> lista = new ArrayList<>();

        for (int i = 0; i < jsonarray.length(); i++) {

            Usuario usuario = new Usuario();
            int id;
            String username, email, password;
            String planet;

            id = jsonarray.getJSONObject(i).optInt("idUsuario");
            username = jsonarray.getJSONObject(i).optString("username");
            email = jsonarray.getJSONObject(i).optString("email");
            password = jsonarray.getJSONObject(i).optString("password");
            planet = jsonarray.getJSONObject(i).optString("planet");

            usuario.setId(id);
            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setPlanet(BasePlanet.stringtoBaseplanet(planet));
            lista.add(usuario);
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Usuario convertirJsonUsuario(JSONObject jsonObject) throws JSONException {

        Usuario usuario = new Usuario();

        usuario.setId(jsonObject.optInt("idUsuario"));
        usuario.setUsername(jsonObject.optString("username"));
        usuario.setEmail(jsonObject.optString("email"));
        if(jsonObject.optString("photo") != null)
            usuario.setPhoto(Base64.getDecoder().decode(jsonObject.optString("photo")));

        return usuario;
    }
}
