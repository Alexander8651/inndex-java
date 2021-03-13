package com.inndex.fragments.promociones.presentador;

import com.inndex.adapter.AdapterPromociones;
import com.inndex.model.Promocion;

import java.util.ArrayList;

public interface IPromocionListFragment {
    void inicializarRv(AdapterPromociones promociones);
    AdapterPromociones crearAdapter(ArrayList<Promocion> promocions);
}
