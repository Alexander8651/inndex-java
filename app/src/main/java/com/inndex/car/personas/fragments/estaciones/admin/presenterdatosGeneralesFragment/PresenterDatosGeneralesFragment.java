package com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.ResponseServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterDatosGeneralesFragment implements IPresenterDataGeneralFragment {

    Context context;

    public PresenterDatosGeneralesFragment(Context context) {
        this.context = context;
    }

    @Override
    public void actualizarDataGeneral(Estaciones estaciones, View view) {

        Call<Estaciones> actualizarDataGeneral = MedidorApiAdapter.getApiService().updateStationGeneralData(estaciones);
        actualizarDataGeneral.enqueue(new Callback<Estaciones>() {
            @Override
            public void onResponse(@NonNull Call<Estaciones> call, @NonNull Response<Estaciones> response) {

                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    if (response.body() != null)
                        estaciones.setId(response.body().getId());
                    bundle.putParcelable("estacionIs", estaciones);
                    Toast.makeText(context, "Se actualizo la información", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.editarEdsFragment, bundle);
                } else {
                    Toast.makeText(context, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Estaciones> call, @NonNull Throwable t) {
                Toast.makeText(context, "ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
