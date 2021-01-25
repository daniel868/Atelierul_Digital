package com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network;

import com.example.atelieruldigitalfinalproject.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClass {
    public static final String KEY = "6587eb40ff9be7a9a28abfb70eb0fa2f";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = getClient();
            httpClient.addInterceptor(new QueryInterceptor("units", "metric"));
            httpClient.addInterceptor(new QueryInterceptor("appid", KEY));

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient.Builder getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        return client;
    }

}
