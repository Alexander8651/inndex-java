package com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
            public void onResponse(Call<ResponseServices> call, Response<ResponseServices> response) {

                if (response.isSuccessful()){
                    ResponseServices responseServices = response.body();
                   // Log.d("meejecuto", responseServices.getMsg());


                    Toast.makeText(context, "Se actualizo el usuario", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigateUp();

                }
            }

            @Override
            public void onFailure(Call<ResponseServices> call, Throwable t) {

            }
        });
    }
}
