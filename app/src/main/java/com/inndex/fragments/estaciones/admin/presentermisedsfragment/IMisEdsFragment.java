package com.inndex.fragments.estaciones.admin.presentermisedsfragment;

import com.inndex.adapter.AdapterMisEds;
import com.inndex.model.Estaciones;

import java.util.ArrayList;

public interface IMisEdsFragment {

    AdapterMisEds crearAdater(ArrayList<Estaciones> estaciones);
    void  InicializarAdapter(AdapterMisEds adapterMisEds);

}
