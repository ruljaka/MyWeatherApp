package com.ruslangrigoriev.weather.domain;

import androidx.lifecycle.LiveData;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;

public class WeatherRepository {
    public WeatherDAO weatherDAO;
    private final LiveData<CurrentWeather> currentWeather;
    private final LiveData<ForecastWeather> forecastWeather;

    public WeatherRepository() {
        this.weatherDAO = App.getInstance().getWeatherDAO();
        this.currentWeather = weatherDAO.getCurrent(1);
        this.forecastWeather = weatherDAO.getForecast(1);
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<ForecastWeather> getForecastWeather() {
        return forecastWeather;
    }

    public void insertCurrentWeather(CurrentWeather currentWeather) {
        App.getInstance().databaseWriteExecutor.execute(() -> weatherDAO.insertCurrent(currentWeather));

    }

    public void insertForecastWeather(ForecastWeather forecastWeather) {
        App.getInstance().databaseWriteExecutor.execute(() -> weatherDAO.insertForecast(forecastWeather));
    }
}
