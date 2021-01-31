package com.inndex.car.personas.retrofit;

import com.inndex.car.personas.utils.IApiBaseUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.inndex.car.personas.utils.Constantes.TIMEOUT;

public class MedidorApiAdapter {

    private static InndexApiServices API_SERVICE;

    public static InndexApiServices getApiService() {

        if (API_SERVICE == null){
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IApiBaseUrl.INNDEX_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            API_SERVICE = retrofit.create(InndexApiServices.class);
        }
        return API_SERVICE;
    }
}