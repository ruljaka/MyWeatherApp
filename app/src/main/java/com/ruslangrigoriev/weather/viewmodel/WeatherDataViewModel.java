package com.ruslangrigoriev.weather.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.Forecast;
import com.ruslangrigoriev.weather.domain.WeatherApiInteractor;

public class WeatherDataViewModel extends ViewModel {
    private MutableLiveData<CurrentWeather> currentLiveData = new MutableLiveData<>();
    private MutableLiveData<Forecast> forecastLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private WeatherApiInteractor weatherApiInteractor = App.getInstance().weatherApiInteractor;

    public void getData(String cityName) {
        weatherApiInteractor.getWeatherData(cityName, new WeatherApiInteractor.GetWeatherDataCallback() {
            @Override
            public void onCurrentSuccess(CurrentWeather currentWeather) {
                currentLiveData.setValue(currentWeather);
                Log.d("MyTag","current onSuccess");
                Util.getInstance().saveCityName(cityName);
            }

            @Override
            public void onForecastSuccess(Forecast forecast) {
                forecastLiveData.setValue(forecast);
                Log.d("MyTag","forecast onSuccess");
            }

            @Override
            public void onError(String error) {
                errorLiveData.setValue(error);
            }
        });
    }

    public LiveData<CurrentWeather> getCurrent(){
        return currentLiveData;
    }

    public LiveData<Forecast> getForecast(){
        return forecastLiveData;
    }

    public LiveData<String> getError(){
        return errorLiveData;
    }

}
