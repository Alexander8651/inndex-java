package com.inndex.car.personas.activities.mainactivity;

import com.inndex.car.personas.activities.mainactivity.adapters.PlacesAdapter;
import com.inndex.car.personas.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;

import java.util.ArrayList;

interface IMainActivity {

    public PlacesAdapter crearAdaptador(ArrayList<LocationResposePlaceFourSquare> miPets);

    public void inicializarAdaptadorRV(PlacesAdapter adaptador);
}
