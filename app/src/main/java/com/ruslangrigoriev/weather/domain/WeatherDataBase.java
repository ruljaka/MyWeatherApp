package com.ruslangrigoriev.weather.domain;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;

@Database(entities = {CurrentWeather.class, ForecastWeather.class}, version = 2, exportSchema = false)
public abstract class WeatherDataBase extends RoomDatabase {

    public abstract WeatherDAO getWeatherDAO();

}
