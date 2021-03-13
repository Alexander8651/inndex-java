package com.inndex.fragments.promociones.presentador;

import android.view.View;
import android.widget.Spinner;

import com.inndex.adapter.AdapterSpinnerLinea;
import com.inndex.model.LineasVehiculos;

import java.util.ArrayList;

public interface IPresenterPromocionForm {
    void mostrarSpinnerTipoOferta();
    void mostrarSpinnerCategoriaOferta();
    void publicarOferta(View view);
}
