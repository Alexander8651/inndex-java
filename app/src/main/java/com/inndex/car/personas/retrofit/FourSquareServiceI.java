package com.inndex.car.personas.retrofit;

import android.location.Location;

import com.inndex.car.personas.model.Ll;
import com.inndex.car.personas.model.Tanqueadas;
import com.inndex.car.personas.retrofit.responseapifoursquare.PlaceVenusFourceSquare;
import com.inndex.car.personas.retrofit.responseapifoursquare.ResponsePlaceApiFourSquare;
import com.inndex.car.personas.utils.Constantes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FourSquareServiceI {


    @GET("search?")
    Call<ResponsePlaceApiFourSquare> getPlacesNear(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("ll") String ll,
            @Query("radius") int radius,
            @Query("query") String query,
            @Query("v") String v,
            @Query("limit") int limit
            );
}
