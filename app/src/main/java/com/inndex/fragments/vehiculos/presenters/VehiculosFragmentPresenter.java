package com.inndex.fragments.vehiculos.presenters;

import android.util.Log;

import com.inndex.fragments.vehiculos.IMisVehiculosFragment;
import com.inndex.model.Vehiculo;
import com.inndex.retrofit.MedidorApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosFragmentPresenter implements IVehiculosFragmentPresenter {

    IMisVehiculosFragment iMisVehiculosFragment;
    ArrayList<Vehiculo> vehiculos;
    private int userID;


    public VehiculosFragmentPresenter(IMisVehiculosFragment iMisVehiculosFragment, int userID) {
        this.iMisVehiculosFragment = iMisVehiculosFragment;
        this.userID = userID;
        obtenerVehiculos();
    }

    @Override
    public void obtenerVehiculos() {

        Long id = (long) userID;

        Call<List<Vehiculo>> apiVehiculos = MedidorApiAdapter.getApiService().getVehiclesByUser(id);

        apiVehiculos.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {

                if (response.isSuccessful() && response != null){
                    vehiculos = (ArrayList<Vehiculo>) response.body();
                    mostrarVehiculos();
                }
            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {

            }
        });

    }

    @Override
    public void mostrarVehiculos() {
        iMisVehiculosFragment.inicializarAdaptador(iMisVehiculosFragment.crearAdaptador(vehiculos));
    }
}
