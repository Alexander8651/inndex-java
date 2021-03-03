package com.inndex.car.personas.fragments.configuracion_cuenta.presenterubicaciontexto;

import android.content.Context;
import android.widget.TextView;

import com.inndex.car.personas.enums.ETextos;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Textos;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterUbicacionTexto implements IPresenterUbicacionTexto {

    IUbicacionTextoFragment iUbicacionTextoFragment;
    Context context;
    String bodyUbicacion;

    public PresenterUbicacionTexto(IUbicacionTextoFragment iUbicacionTextoFragment, Context context) {
        this.iUbicacionTextoFragment = iUbicacionTextoFragment;
        this.context = context;

        obtenerBodyUbicaciobText();
    }


    @Override
    public void obtenerBodyUbicaciobText() {

        Call<Textos> textosCall = MedidorApiAdapter.getApiService().getTextoById(ETextos.UBICACION.getId());

        textosCall.enqueue(new Callback<Textos>() {
            @Override
            public void onResponse(Call<Textos> call, Response<Textos> response) {

                if (response.isSuccessful()){
                    bodyUbicacion = response.body().getTexto();
                    mostrarBodyUbicaciobText();
                }

            }

            @Override
            public void onFailure(Call<Textos> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarBodyUbicaciobText() {

        TextView body = iUbicacionTextoFragment.crearTextviewBody();

        body.setText(bodyUbicacion);

    }
}
