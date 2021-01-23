package com.inndex.car.personas.activities.mainactivity;

import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.estaciones.EstacionesServiciosFragment;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Ll;
import com.inndex.car.personas.retrofit.FourSquareService;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;
import com.inndex.car.personas.retrofit.responseapifoursquare.PlaceVenusFourceSquare;
import com.inndex.car.personas.retrofit.responseapifoursquare.ResponsePlaceApiFourSquare;
import com.inndex.car.personas.services.InndexLocationService;
import com.inndex.car.personas.utils.Constantes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presentador implements IPresentador {

    private IMainActivity iMainactivity;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String formattedDate = df.format(c.getTime());
    RecyclerView recycler;


    ArrayList<LocationResposePlaceFourSquare> miPlaces;

    public void setMiPlaces(ArrayList<LocationResposePlaceFourSquare> miPlaces) {
        mostrarContactosRV();
        this.miPlaces = miPlaces;
    }

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
