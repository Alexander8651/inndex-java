package com.inndex.car.personas.retrofit;

import com.inndex.car.personas.utils.Constantes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FourSquareService {

    private static FourSquareServiceI API_SERVICE;

    public static FourSquareServiceI getApiService() {

        if (API_SERVICE == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL_FOURSQUARE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            API_SERVICE = retrofit.create(FourSquareServiceI.class);
        }
        return API_SERVICE;
    }
}
