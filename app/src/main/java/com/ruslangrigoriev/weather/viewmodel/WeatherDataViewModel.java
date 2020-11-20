package com.ruslangrigoriev.weather.viewmodel;

import android.content.Intent;
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
import com.ruslangrigoriev.weather.data.entities.Forecast;
import com.ruslangrigoriev.weather.domain.WeatherApiInteractor;
import com.ruslangrigoriev.weather.widget.WeatherWidgetProvider;

public class WeatherDataViewModel extends ViewModel implements LifecycleObserver {
    private static final int DELAY = 300000;
    public static final String MY_TAG = "MyTag";
    private static final String UPDATE_ALL_WIDGETS = "update_all_widgets";

    private MutableLiveData<CurrentWeather> currentLiveData = new MutableLiveData<>();
    private MutableLiveData<Forecast> forecastLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private WeatherApiInteractor weatherApiInteractor = App.getInstance().weatherApiInteractor;

    private Handler handler = new Handler();

    private Runnable taskRunnable = new Runnable() {
        @Override
        public void run() {
            getData(Util.getInstance().getCityName());
            handler.postDelayed(taskRunnable, DELAY);
        }
    };

    public void getData(String cityName) {
        weatherApiInteractor.getWeatherData(cityName, new WeatherApiInteractor.GetWeatherDataCallback() {
            @Override
            public void onCurrentSuccess(CurrentWeather currentWeather) {
                currentLiveData.setValue(currentWeather);
                Log.d(MY_TAG," ViewModel current onSuccess");
                Util.getInstance().saveCityName(cityName);


                //update Widget
                Intent intent = new Intent(App.getInstance(), WeatherWidgetProvider.class);
                intent.setAction(UPDATE_ALL_WIDGETS);
                App.getInstance().sendBroadcast(intent);
                Log.d(MY_TAG, "sendBroadcast");
            }

            @Override
            public void onForecastSuccess(Forecast forecast) {
                forecastLiveData.setValue(forecast);
                //Log.d(MY_TAG," ViewModel forecast onSuccess");
            }

            @Override
            public void onError(String error) {
                errorLiveData.setValue(error);
            }
        });
    }

    public LiveData<CurrentWeather> getCurrent() {
        return currentLiveData;
    }

    public LiveData<Forecast> getForecast() {
        return forecastLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLifecycleResume() {
        //Log.d(MY_TAG, "onResume");
        getData(Util.getInstance().getCityName());
        handler.postDelayed(taskRunnable, DELAY);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onLifecyclePaused() {
        //Log.d(MY_TAG, "onPause");
        handler.removeCallbacksAndMessages(null);
    }

}
