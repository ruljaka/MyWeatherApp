package com.ruslangrigoriev.weather.data.entities;

public class AllWeatherData {
    private CurrentWeather currentWeather;
    private Forecast forecast;

    public AllWeatherData(CurrentWeather currentWeather, Forecast forecast) {
        this.currentWeather = currentWeather;
        this.forecast = forecast;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
