package com.example.stelaris.bbdd;

import com.example.stelaris.clases.Usuario;
import com.example.stelaris.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Usuarios {

    public static List<Usuario> convertirJsonUsuario(JSONArray jsonarray) throws JSONException {

        List<Usuario> lista = new ArrayList<>();

        for (int i = 0; i < jsonarray.length(); i++) {

            Usuario usuario = new Usuario();
            int id;
            String username, email, password;

            id = jsonarray.getJSONObject(i).optInt("idUsuario");
            username = jsonarray.getJSONObject(i).optString("username");
            email = jsonarray.getJSONObject(i).optString("email");
            password = jsonarray.getJSONObject(i).optString("password");

            usuario.setId(id);
            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setPassword(password);
            lista.add(usuario);
        }
        return lista;
    }

    public static String recuperarContenido(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String resultado = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse respuesta;
        InputStream stream = null;
        try {
            respuesta = httpclient.execute(httpget);
            HttpEntity entity = respuesta.getEntity();

            if (entity != null) {
                stream = entity.getContent();
                resultado = Utils.convertirInputToString(stream);
            }
        } catch (Exception ignored) {

        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ignored) {

            }
        }
        return resultado;
    }
}
