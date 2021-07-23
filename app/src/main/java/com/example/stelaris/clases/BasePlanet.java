package com.example.stelaris.clases;

import java.io.Serializable;

public enum BasePlanet implements Serializable {

    tierra, favoritos, nasa;

    public static BasePlanet stringtoBaseplanet(String string){
        switch (string){
            case "tierra":
                return BasePlanet.tierra;
            case "favoritos":
                return BasePlanet.favoritos;
            case "nasa":
                return BasePlanet.nasa;
            default:
                return null;
        }
    }

    public static String baseplanetToString(BasePlanet planet){
        switch (planet){
            case tierra:
                return "tierra";
            case favoritos:
                return "favoritos";
            case nasa:
                return "nasa";
            default:
                return null;
        }
    }
}
