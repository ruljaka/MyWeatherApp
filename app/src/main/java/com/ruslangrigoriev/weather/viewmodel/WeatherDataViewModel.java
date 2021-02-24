package com.ruslangrigoriev.weather.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;
import com.ruslangrigoriev.weather.domain.WeatherRepository;


public class WeatherDataViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final LiveData<CurrentWeather> currentLiveData;
    private final LiveData<ForecastWeather> forecastLiveData;
    private final MutableLiveData<String> errorLiveData;

    public WeatherDataViewModel() {
        super();
        this.weatherRepository = App.getInstance().getWeatherRepository();
        this.currentLiveData = weatherRepository.getCurrentWeather();
        this.forecastLiveData = weatherRepository.getForecastWeather();
        this.errorLiveData = weatherRepository.getErrorLiveData();
    }

    public void getData(String cityName) {
        weatherRepository.getApiData(cityName);
    }

    public LiveData<CurrentWeather> getCurrent() {
        return currentLiveData;
    }

    public LiveData<ForecastWeather> getForecast() {
        return forecastLiveData;
    }

    public MutableLiveData<String> getError() {
        return errorLiveData;
    }

}
