package com.inndex.activities.mainactivity;

import com.inndex.activities.mainactivity.adapters.PlacesAdapter;
import com.inndex.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;

import java.util.ArrayList;

interface IMainActivity {

    public PlacesAdapter crearAdaptador(ArrayList<LocationResposePlaceFourSquare> miPets);

    public void inicializarAdaptadorRV(PlacesAdapter adaptador);
}
