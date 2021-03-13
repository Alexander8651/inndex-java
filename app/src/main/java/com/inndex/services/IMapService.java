package com.inndex.services;

import com.inndex.model.Estaciones;

public interface IMapService {

    void onEstacionMarkerClick(Estaciones estacion);

    void onRutaTrazada();

    void goToStreetView(String location);

}
