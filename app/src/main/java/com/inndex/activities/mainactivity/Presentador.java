package com.inndex.activities.mainactivity;

import android.icu.text.SimpleDateFormat;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.inndex.retrofit.FourSquareService;
import com.inndex.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;
import com.inndex.retrofit.responseapifoursquare.ResponsePlaceApiFourSquare;
import com.inndex.utils.Constantes;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presentador implements IPresentador {

    private IMainActivity iMainactivity;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df;
    String formattedDate;
    RecyclerView recycler;


    ArrayList<LocationResposePlaceFourSquare> miPlaces;

    public Presentador(IMainActivity iMainactivity, RecyclerView recycler) {
        this.iMainactivity = iMainactivity;
        this.recycler = recycler;

        miPlaces = new ArrayList<>();
    }

    @Override
    public void getPlacesNear(String place) {
        this.recycler.setVisibility(View.VISIBLE);
        Call<ResponsePlaceApiFourSquare> getPlacesNear = FourSquareService.getApiService().getPlacesNear(
                Constantes.FORESQUARE_CLIENT_ID,
                Constantes.FORESQUARE_CLIENT_SECRET,
                "10.48414391439602,-73.2676873802564",
                1000,
                place,
                formattedDate,
                Constantes.LIMIT_RESPONSE
        );


        getPlacesNear.enqueue(new Callback<ResponsePlaceApiFourSquare>() {
            @Override
            public void onResponse(Call<ResponsePlaceApiFourSquare> call, Response<ResponsePlaceApiFourSquare> response) {


                if (response != null && response.isSuccessful()) {

                    if (response.body().getResponse().getPlaces().size() > 0){
                        miPlaces = (ArrayList<LocationResposePlaceFourSquare>)response.body().getResponse().getPlaces();
                        mostrarContactosRV();

                    }
                }


            }

            @Override
            public void onFailure(Call<ResponsePlaceApiFourSquare> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarContactosRV() {
        iMainactivity.inicializarAdaptadorRV(iMainactivity.crearAdaptador(miPlaces));

    }


}
