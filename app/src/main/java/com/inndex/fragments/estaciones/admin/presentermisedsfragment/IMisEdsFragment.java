package com.inndex.fragments.estaciones.admin.presentermisedsfragment;

import com.inndex.adapter.AdapterMisEds;
import com.inndex.model.Estaciones;

import java.util.ArrayList;
import java.util.List;

public interface IMisEdsFragment {

    AdapterMisEds crearAdater(List<Estaciones> estaciones);
    void  InicializarAdapter(AdapterMisEds adapterMisEds);

}
