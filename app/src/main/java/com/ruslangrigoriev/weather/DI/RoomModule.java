package com.ruslangrigoriev.weather.DI;

import android.app.Application;

import androidx.room.Room;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.domain.WeatherDAO;
import com.ruslangrigoriev.weather.domain.WeatherDataBase;
import com.ruslangrigoriev.weather.domain.WeatherDataSource;
import com.ruslangrigoriev.weather.domain.WeatherRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private final WeatherDataBase weatherDataBase;

    public RoomModule(Application application) {
        this.weatherDataBase = Room.databaseBuilder(
                application,
                WeatherDataBase.class,
                "weather_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    WeatherDataBase provideWeatherDataBase(){
        return weatherDataBase;
    }

    @Singleton
    @Provides
    WeatherDAO provideWeatherDAO(WeatherDataBase weatherDataBase) {
        return weatherDataBase.weatherDAO();
    }

    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository(){
        return new WeatherDataSource();
    }




}
