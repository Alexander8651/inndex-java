package com.inndex.car.personas.fragments.promociones.presentador;

import com.inndex.car.personas.adapter.AdapterPromociones;
import com.inndex.car.personas.model.Promocion;

import java.util.ArrayList;

public interface IPromocionListFragment {
    void inicializarRv(AdapterPromociones promociones);
    AdapterPromociones crearAdapter(ArrayList<Promocion> promocions);
}
