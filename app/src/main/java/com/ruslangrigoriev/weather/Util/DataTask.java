package com.ruslangrigoriev.weather.Util;

import android.os.AsyncTask;
import android.util.Log;

import com.ruslangrigoriev.weather.MyEventListener;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.network.NetworkService;

import java.io.IOException;

import retrofit2.Call;

public class DataTask extends AsyncTask<Void, Void, Void> {
    public static final String MY_TAG = "MyTag";
    private final static String DAYS = "6";
    private final static String KEY = "5a7d8bef29c34458bc0f3e60f0cbefcd";
    private final String lang;
    private final String city;

    private final MyEventListener callback;
    private Forecast forecast;
    private CurrentWeather currentWeather;

    public DataTask(MyEventListener cb, String city, String lang) {
        callback = cb;
        this.city = city;
        this.lang = lang;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Call<CurrentWeather> callCurrent = NetworkService.getInstance()
                .getWeatherApi()
                .getCurrentByCity(city, lang, DAYS, KEY);
        try {
            currentWeather = callCurrent.execute().body();
            Log.d(MY_TAG, "done");
        } catch (IOException e) {
            Log.d(MY_TAG, "failure");
            e.printStackTrace();
        }

        Call<Forecast> callForecast = NetworkService.getInstance()
                .getWeatherApi()
                .getForecastByCity(city, lang, DAYS, KEY);
        try {
            forecast = callForecast.execute().body();
            Log.d(MY_TAG, "done");
        } catch (IOException e) {
            Log.d(MY_TAG, "failure");
            e.printStackTrace();
        }

        Log.d(MY_TAG, "doinDone");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(MY_TAG, "onPost");
        if (callback != null) {
            callback.onApiDataReceived();
        }
    }

    public Forecast getForecast() {
        return forecast;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
}
