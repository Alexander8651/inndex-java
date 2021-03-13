package com.inndex.fragments.informacion.presenterautorizacion;

import android.content.Context;

import com.inndex.enums.ETextos;
import com.inndex.model.Textos;
import com.inndex.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterAutorizacion implements IPresenterAutorizacion {

    IAutorizacionFragment iPresenterAutorizacion;
    Context context;
    String body;

    public PresenterAutorizacion(IAutorizacionFragment iPresenterAutorizacion, Context context) {
        this.iPresenterAutorizacion = iPresenterAutorizacion;
        this.context = context;

        obtenerAutorizacion();
    }

    @Override
    public void obtenerAutorizacion() {

        Call<Textos> textosCall = MedidorApiAdapter.getApiService().getTextoById(ETextos.AUTORIZACION_USUARIOS.getId());

        textosCall.enqueue(new Callback<Textos>() {
            @Override
            public void onResponse(Call<Textos> call, Response<Textos> response) {

                if (response.isSuccessful()){
                    body = response.body().getTexto();
                    mostrarAutorizacion();
                }

            }

            @Override
            public void onFailure(Call<Textos> call, Throwable t) {

            }
        });

    }

    @Override
    public void mostrarAutorizacion() {
        iPresenterAutorizacion.creatTextviewBody().setText(body);

    }
}
