package com.inndex.fragments.vehiculos.presenters;

import com.inndex.model.MarcaVehiculos;

import java.util.ArrayList;

public interface IPresenterAgregarVehiculos {

    void obtenerMarcas();
    void mostrarSpinnerMarca();
    void obtenerLinea(int id);
    void mostrarSpinnerLinea();
    void mostrarSpinnerModelo();

    void obtenerCombustibles();
    void mostrarSpinnerCombustibles();

    void obtenerColorVehiculo(int color, String colorHex);

    Boolean agregarVehiculo();
}
