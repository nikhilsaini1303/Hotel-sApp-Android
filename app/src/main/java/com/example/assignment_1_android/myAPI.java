package com.example.assignment_1_android;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface myAPI {
    @GET("locations/v3/search")
    Call<HotelData> getAllData(
            @Query("q") String query,
            @Query("locale") String locale,
            @Query("langid") int langid,
            @Query("siteid") String siteid,
            @Header("x-rapidapi-host") String host,
            @Header("x-rapidapi-key") String apiKey
    );

}
