package com.example.atelierul_digital.week_7;

import com.example.atelierul_digital.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClass {
    public static final String KEY = "263148369e9444853f529af20881d2bc";
    public static final String BASE_URL = "https://api.themoviedb.org/";

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = getClient();
            httpClient.addInterceptor(new QueryInterceptor("api_key", KEY));
            httpClient.addInterceptor(new QueryInterceptor("language", "en-US"));

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
