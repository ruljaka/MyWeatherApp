package com.ruslangrigoriev.weather.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;

public interface WeatherRepository  {

    void insertCurrentWeather(CurrentWeather currentWeather);

    void insertForecastWeather(ForecastWeather forecastWeather);

    void getApiData(String cityName);

    LiveData<CurrentWeather> getCurrentWeather();

    LiveData<ForecastWeather> getForecastWeather();

    MutableLiveData<String> getErrorLiveData();

    void cancelDispose();
}

