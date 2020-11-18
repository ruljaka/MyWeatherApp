package com.ruslangrigoriev.weather.data;



import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

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
