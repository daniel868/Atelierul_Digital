package com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.Network;

import com.example.atelieruldigitalfinalproject.DataPackage.WeatherAPI.DataAPI.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    String queryParam = "q";

    @GET("weather")
    Call<WeatherData> getDataByCity(@Query(queryParam) String cityName);

}
