package com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment;

import com.inndex.car.personas.adapter.AdapterMisEds;
import com.inndex.car.personas.model.Estaciones;

import java.util.ArrayList;

public interface IMisEdsFragment {

    AdapterMisEds crearAdater(ArrayList<Estaciones> estaciones);
    void  InicializarAdapter(AdapterMisEds adapterMisEds);

}
