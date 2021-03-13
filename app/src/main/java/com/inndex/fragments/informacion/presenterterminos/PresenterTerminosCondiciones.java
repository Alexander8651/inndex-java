package com.inndex.fragments.informacion.presenterterminos;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.inndex.enums.ETextos;
import com.inndex.model.Textos;
import com.inndex.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterTerminosCondiciones  implements  IPresenterTerminosCondiciones{

    ITerminosCondicionesFradment iTerminosCondicionesFradment;
    Context context;
    String body;

    public PresenterTerminosCondiciones(ITerminosCondicionesFradment iTerminosCondicionesFradment, Context context) {
        this.iTerminosCondicionesFradment = iTerminosCondicionesFradment;
        this.context = context;
        obtenerBodyHeader();
    }

    @Override
    public void obtenerBodyHeader() {

        Call<Textos> textosCall = MedidorApiAdapter.getApiService().getTextoById(ETextos.TERMINOS_Y_CONDICIONES.getId());

        textosCall.enqueue(new Callback<Textos>() {
            @Override
            public void onResponse(Call<Textos> call, Response<Textos> response) {


                if (response.isSuccessful()){
                    body = response.body().getTexto();
                    mostrarBodyHeader();
                }

            }

            @Override
            public void onFailure(Call<Textos> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarBodyHeader() {
        iTerminosCondicionesFradment.crearTexviewBodyTerminos().setText(body);
    }
}
