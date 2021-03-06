package com.inndex.fragments.vehiculos.presenters;

import android.widget.Spinner;
import com.inndex.adapter.AdapterSpinner;
import com.inndex.adapter.AdapterSpinnerCombustible;
import com.inndex.adapter.AdapterSpinnerLinea;
import com.inndex.model.Combustibles;
import com.inndex.model.LineasVehiculos;
import com.inndex.model.MarcaVehiculos;

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

