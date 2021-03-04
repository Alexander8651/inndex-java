package com.inndex.car.personas.fragments.informacion.presenterpoliticasprivacidad;

import android.content.Context;

import com.inndex.car.personas.enums.ETextos;
import com.inndex.car.personas.fragments.informacion.presenterterminos.IPresenterTerminosCondiciones;
import com.inndex.car.personas.model.Textos;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterPoliticas implements IPresenterPolicas {
    IPoliticasPrivacidadFragment iPoliticasPrivacidadFragment;
    Context context;
    String body;

    public PresenterPoliticas(IPoliticasPrivacidadFragment iPoliticasPrivacidadFragment, Context context) {
        this.iPoliticasPrivacidadFragment = iPoliticasPrivacidadFragment;
        this.context = context;

        obtenerPoliticasPrivacidad();
    }

    @Override
    public void obtenerPoliticasPrivacidad() {

        Call<Textos> textosCall = MedidorApiAdapter.getApiService().getTextoById(ETextos.TRATAMIENTO_DE_DATOS.getId());

        textosCall.enqueue(new Callback<Textos>() {
            @Override
            public void onResponse(Call<Textos> call, Response<Textos> response) {

                if (response.isSuccessful()){
                    body = response.body().getTexto();
                    mostrarPoliticasPrivacidad();
                }

            }

            @Override
            public void onFailure(Call<Textos> call, Throwable t) {

            }
        });

    }

    @Override
    public void mostrarPoliticasPrivacidad() {

        iPoliticasPrivacidadFragment.crearTextviewPoliticasPrivacidad().setText(body);

    }
}
