package com.ruslangrigoriev.weather;

import android.app.Application;

import com.ruslangrigoriev.weather.data.WeatherApiService;
import com.ruslangrigoriev.weather.data.WeatherRepository;
import com.ruslangrigoriev.weather.domain.WeatherApiInteractor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App instance;
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";

    public WeatherApiService weatherApiService;
    public WeatherApiInteractor weatherApiInteractor;
    public WeatherRepository weatherRepository = new WeatherRepository();

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
        initInteractor();
    }

    private void initRetrofit() {
        weatherApiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService.class);
    }

    private void initInteractor() {
        weatherApiInteractor = new WeatherApiInteractor(weatherApiService, weatherRepository);
    }
}
