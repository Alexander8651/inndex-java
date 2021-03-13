package com.inndex.fragments.promociones.presentador;



import android.util.Log;

import com.inndex.model.Promocion;
import com.inndex.retrofit.MedidorApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreseenterPromocionList implements IPresenterPromocionList {

    IPromocionListFragment iPromocionListFragment;
    Long idEstacion;

    ArrayList<Promocion> promocions;

    public PreseenterPromocionList(IPromocionListFragment iPromocionListFragment, Long idEstacion) {
        this.iPromocionListFragment = iPromocionListFragment;
        this.idEstacion = idEstacion;
        obtenerPromociones();
    }

    @Override
    public void obtenerPromociones() {
        Call<List<Promocion>> promocion = MedidorApiAdapter.getApiService().getPromocionesByEstacionId(idEstacion);

        promocion.enqueue(new Callback<List<Promocion>>() {
            @Override
            public void onResponse(Call<List<Promocion>> call, Response<List<Promocion>> response) {
                if (response.isSuccessful()){
                    promocions = (ArrayList<Promocion>) response.body();
                    Log.d("promocioness", String.valueOf(response.body().size()));
                    mostrarPromociones();
                }
            }

            @Override
            public void onFailure(Call<List<Promocion>> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarPromociones() {
        iPromocionListFragment.inicializarRv(iPromocionListFragment.crearAdapter(promocions));
    }
}


