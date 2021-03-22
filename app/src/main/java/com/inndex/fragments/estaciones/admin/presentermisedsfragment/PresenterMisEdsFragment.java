package com.inndex.fragments.estaciones.admin.presentermisedsfragment;

import android.util.Log;

import com.inndex.model.Estaciones;
import com.inndex.retrofit.MedidorApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterMisEdsFragment implements IPresenterMisEdsFragment {

    ArrayList<Estaciones> estaciones;
    IMisEdsFragment iMisEdsFragment;
    long userID;

    public PresenterMisEdsFragment( IMisEdsFragment iMisEdsFragment, long userID) {
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
                }
                mostrarEds();
            }

            @Override
            public void onFailure(Call<List<Estaciones>> call, Throwable t) {
                mostrarEds();
            }
        });
    }

    @Override
    public void mostrarEds() {
        iMisEdsFragment.InicializarAdapter(iMisEdsFragment.crearAdater(estaciones));
    }
}
