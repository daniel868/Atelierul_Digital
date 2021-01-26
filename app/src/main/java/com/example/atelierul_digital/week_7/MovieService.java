package com.example.atelierul_digital.week_7;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    String PAGE_QUERY = "page";

    @GET("/3/movie/top_rated")
    Call<Example> getTopRatedMovies(@Query(PAGE_QUERY) int page);

    @GET("/3/movie/upcoming")
    Call<Example> getUpcomingMovies(@Query(PAGE_QUERY) int page);

    @GET("/3/movie/now_playing")
    Call<Example> getNowPlayingMovies(@Query(PAGE_QUERY) int page);
}
