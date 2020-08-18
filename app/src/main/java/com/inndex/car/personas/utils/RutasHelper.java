package com.inndex.car.personas.utils;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.inndex.car.personas.places.EstacionesPlaces;

import java.util.List;

public class RutasHelper {

    private Context context;


    private void drawLine(){

    }

    public static final float getDistanciaRecorrida(List<LatLng> listRutas){

        EstacionesPlaces places = new EstacionesPlaces();
        if (listRutas.size() > 1){

            return places.getDistance(listRutas.get(0), listRutas.get(listRutas.size()-1));
        }else{
            return 0;
        }
    }
}
