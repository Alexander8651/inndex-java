package com.inndex.car.personas.services;

import com.inndex.car.personas.model.Estaciones;

public interface IMapService {

    void onEstacionMarkerClick(Estaciones estacion);

    void onRutaTrazada();
}
