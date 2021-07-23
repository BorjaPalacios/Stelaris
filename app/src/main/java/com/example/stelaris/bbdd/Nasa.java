package com.example.stelaris.bbdd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Nasa {

    public static List<String> convertirJsonNasa(JSONObject jsonObject) throws JSONException {

        List<String> lista = new ArrayList<>();
        JSONArray items = jsonObject.getJSONObject("collection").getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONArray links = items.getJSONObject(i).getJSONArray("links");
            for (int j = 0; j < links.length(); j++) {
                lista.add(links.getJSONObject(j).optString("href"));
            }
        }
        return lista;
    }
}
