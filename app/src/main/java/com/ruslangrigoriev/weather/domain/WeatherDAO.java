package com.ruslangrigoriev.weather.domain;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;

@Dao
public interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrent(CurrentWeather currentWeather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecast(ForecastWeather forecast);

    @Query("SELECT * FROM current_weather WHERE id = :id LIMIT 1")
    LiveData<CurrentWeather> getCurrent(int id);

    @Query("SELECT * FROM forecastWeather WHERE id = :id")
    LiveData<ForecastWeather> getForecast(int id);
}
