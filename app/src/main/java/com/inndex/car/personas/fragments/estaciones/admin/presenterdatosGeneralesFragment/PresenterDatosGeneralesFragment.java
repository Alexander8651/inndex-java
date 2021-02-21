package com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

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

        Call<ResponseServices> actualizarDataGeneral = MedidorApiAdapter.getApiService().updateStationGeneralData(estaciones);

        actualizarDataGeneral.enqueue(new Callback<ResponseServices>() {
            @Override
            public void onResponse(@NonNull Call<ResponseServices> call, @NonNull Response<ResponseServices> response) {

                if (response.isSuccessful()){
                    Toast.makeText(context, "Se actualizo la información", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigateUp();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseServices> call, @NonNull Throwable t) {

            }
        });
    }
}
