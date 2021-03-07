package com.inndex.car.personas.fragments.promociones.presentador;

import android.view.View;
import android.widget.Spinner;

import com.inndex.car.personas.adapter.AdapterSpinnerLinea;
import com.inndex.car.personas.model.LineasVehiculos;

import java.util.ArrayList;

public interface IPresenterPromocionForm {
    void mostrarSpinnerTipoOferta();
    void mostrarSpinnerCategoriaOferta();
    void publicarOferta(View view);
}
