package com.inndex.fragments.vehiculos;

import com.inndex.adapter.AdapterVehiculo;
import com.inndex.model.Vehiculo;

import java.util.ArrayList;

public interface IMisVehiculosFragment {

    AdapterVehiculo crearAdaptador(ArrayList<Vehiculo> vehiculos);
    void inicializarAdaptador(AdapterVehiculo adapterVehiculo);
}
