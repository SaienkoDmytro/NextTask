package com.example.nexttask.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("/locations/v1/cities/search")
    Call<List<City>> searchCity(@Query("apikey") String apikey, @Query("q") String city);
}
