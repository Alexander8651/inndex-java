package com.inndex.retrofit.distanceapi;

import com.inndex.dto.distance.DistanceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceApiServices {

    @GET("json")
    Call<DistanceApiResponse> getDistanceBetween(@Query("origins") String origins,
                                                 @Query("destinations") String destinations,
                                                 @Query("key") String key);
}
