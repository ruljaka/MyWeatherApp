package com.ruslangrigoriev.weather.data.entities;

public class AllWeatherData {
    private final CurrentWeather currentWeather;
    private final ForecastWeather forecastWeather;

    public AllWeatherData(CurrentWeather currentWeather, ForecastWeather forecastWeather) {
        this.currentWeather = currentWeather;
        this.forecastWeather = forecastWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public ForecastWeather getForecastWeather() {
        return forecastWeather;
    }

}
