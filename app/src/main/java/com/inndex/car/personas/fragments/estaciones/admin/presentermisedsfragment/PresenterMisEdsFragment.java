package com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment;

import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterMisEdsFragment implements IPresenterMisEdsFragment {

    ArrayList<Estaciones> estaciones;
    IMisEdsFragment iMisEdsFragment;
    int userID;

    public PresenterMisEdsFragment( IMisEdsFragment iMisEdsFragment, int userID) {
        this.iMisEdsFragment = iMisEdsFragment;
        this.userID = userID;
        obtenerEds();
    }

    @Override
    public void obtenerEds() {

        Long userid = (long) userID;
        Call<List<Estaciones>> estacionesByUserAdmin= MedidorApiAdapter.getApiService().getEstacionesByUserAdmin(userid);

        estacionesByUserAdmin.enqueue(new Callback<List<Estaciones>>() {
            @Override
            public void onResponse(Call<List<Estaciones>> call, Response<List<Estaciones>> response) {

                if (response.isSuccessful()){
                    estaciones = (ArrayList<Estaciones>) response.body();
                    mostrarEds();
                }
            }

            @Override
            public void onFailure(Call<List<Estaciones>> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarEds() {
        iMisEdsFragment.InicializarAdapter(iMisEdsFragment.crearAdater(estaciones));

    }
}
