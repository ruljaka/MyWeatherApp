package com.ruslangrigoriev.weather.data;


import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.Forecast;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("forecast/daily")
    Single<Response<Forecast>> getForecastByCity(@Query("city") String city,
                                       @Query("lang") String lang,
                                       @Query("days") String days,
                                       @Query("key") String key);

    @GET("current")
    Single<Response<CurrentWeather>> getCurrentByCity(@Query("city") String city,
                                                     @Query("lang") String lang,
                                                     @Query("key") String key);
}
