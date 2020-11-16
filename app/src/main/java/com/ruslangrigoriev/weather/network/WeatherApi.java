package com.ruslangrigoriev.weather.network;

import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("forecast/daily")
    Call<Forecast> getForecastByCity(@Query("city") String city,
                                     @Query("lang") String lang,
                                     @Query("days") String days,
                                     @Query("key") String key);

    @GET("current")
    Call<CurrentWeather> getCurrentByCity(@Query("city") String city,
                                          @Query("lang") String lang,
                                          @Query("key") String key);
}
