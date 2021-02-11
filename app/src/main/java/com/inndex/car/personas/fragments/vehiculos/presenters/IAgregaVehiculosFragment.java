package com.inndex.car.personas.fragments.vehiculos.presenters;

import android.widget.Spinner;
import com.inndex.car.personas.adapter.AdapterSpinner;
import com.inndex.car.personas.adapter.AdapterSpinnerCombustible;
import com.inndex.car.personas.adapter.AdapterSpinnerLinea;
import com.inndex.car.personas.model.Combustibles;
import com.inndex.car.personas.model.LineasVehiculos;
import com.inndex.car.personas.model.MarcaVehiculos;

import java.util.ArrayList;

public interface IAgregaVehiculosFragment {

    Spinner crearSpinerMarca();
    AdapterSpinner crearAdapterMarca(ArrayList<MarcaVehiculos> marcaVehiculos);

    Spinner crearSpinerLinea();
    AdapterSpinnerLinea crearAdapterLinea(ArrayList<LineasVehiculos> marcaVehiculos);

    Spinner crearSpinerModelo();

    Spinner crearSpinerCombuatible();
    AdapterSpinnerCombustible crearAdapterCombustibles(ArrayList<Combustibles> combustibles);
}

