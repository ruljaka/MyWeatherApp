package com.ruslangrigoriev.weather.viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;
import com.ruslangrigoriev.weather.domain.WeatherRepository;

import javax.inject.Inject;


public class WeatherDataViewModel extends ViewModel implements LifecycleObserver {

    private static final int DELAY = 300000;
    private static final String MY_TAG = "MyTag";

    @Inject
    public WeatherRepository weatherRepository;

    private final LiveData<CurrentWeather> currentLiveData;
    private final LiveData<ForecastWeather> forecastLiveData;
    private final MutableLiveData<String> errorLiveData;

    private final Handler handler = new Handler();

    private final Runnable taskRunnable = new Runnable() {
        @Override
        public void run() {
            getData(Util.getCityName());
            handler.postDelayed(taskRunnable, DELAY);
        }
    };

    public WeatherDataViewModel() {
        super();
        //this.weatherRepository = App.getInstance().getWeatherRepository();
        App.getInstance().getAppComponent().inject(this);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLifecycleResume() {
        //start auto update data
        Log.d(MY_TAG, "onLifecycleResume :: getApiData ");
        getData(Util.getCityName());
        handler.postDelayed(taskRunnable, DELAY);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onLifecyclePaused() {
        //stop auto update data
        handler.removeCallbacksAndMessages(null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onLifecycleDestroy() {
        //cancel ZIP
        weatherRepository.cancelDispose();
        Log.d(MY_TAG, "onLifecycleDestroy :: ZIP dispose");
    }

}
