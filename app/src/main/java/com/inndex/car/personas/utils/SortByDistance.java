package com.inndex.car.personas.utils;

import com.inndex.car.personas.model.Estaciones;

import java.util.Comparator;

public class SortByDistance implements Comparator<Estaciones> {
    @Override
    public int compare(Estaciones e1, Estaciones e2) {
        return Float.compare(e2.getDistancia(), e1.getDistancia());
    }
}
