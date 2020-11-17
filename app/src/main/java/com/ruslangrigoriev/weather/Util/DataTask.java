package com.ruslangrigoriev.weather.Util;

import android.os.AsyncTask;
import android.util.Log;

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

    private final DataListener dataListener;
    private Forecast forecast;
    private CurrentWeather currentWeather;

    public DataTask(DataListener dataListener, String city, String lang) {
        this.dataListener = dataListener;
        this.city = city;
        this.lang = lang;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Call<CurrentWeather> callCurrent = NetworkService.getInstance()
                .getWeatherApi()
                .getCurrentByCity(city, lang, KEY);
        try {
            currentWeather = callCurrent.execute().body();
            Log.d(MY_TAG, " current done");
        } catch (IOException e) {
            Log.d(MY_TAG, "current failure");
            e.printStackTrace();
        }

        Call<Forecast> callForecast = NetworkService.getInstance()
                .getWeatherApi()
                .getForecastByCity(city, lang, DAYS, KEY);
        try {
            forecast = callForecast.execute().body();
            Log.d(MY_TAG, "forecast done");
        } catch (IOException e) {
            Log.d(MY_TAG, "forecast failure");
            e.printStackTrace();
        }
        Log.d(MY_TAG, "doInBackground Done");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(MY_TAG, "onPost Done");
        if (dataListener != null) {
            dataListener.onApiDataReceived();
        } else {
            dataListener.onRequestFailed();
        }
    }


    public Forecast getForecast() {
        return forecast;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public interface DataListener {
        void onApiDataReceived();

        void onRequestFailed();
    }
}
