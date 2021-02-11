package com.inndex.car.personas.fragments.vehiculos;

import com.inndex.car.personas.adapter.AdapterVehiculo;
import com.inndex.car.personas.model.Vehiculo;

import java.util.ArrayList;

public interface IMisVehiculosFragment {

    AdapterVehiculo crearAdaptador(ArrayList<Vehiculo> vehiculos);
    void inicializarAdaptador(AdapterVehiculo adapterVehiculo);
}
