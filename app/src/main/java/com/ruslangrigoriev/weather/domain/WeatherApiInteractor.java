package com.ruslangrigoriev.weather.domain;

import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.data.WeatherApiService;
import com.ruslangrigoriev.weather.data.WeatherRepository;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApiInteractor {
    private WeatherApiService weatherApiService;
    private WeatherRepository weatherRepository;
    public static final String MY_TAG = "MyTag";
    private final static String DAYS = "6";
    private final static String KEY = "5a7d8bef29c34458bc0f3e60f0cbefcd";
    String lang = Resources.getSystem().getConfiguration().locale.getLanguage();

    public WeatherApiInteractor(WeatherApiService weatherApiService, WeatherRepository weatherRepository) {
        this.weatherApiService = weatherApiService;
        this.weatherRepository = weatherRepository;

    }

    public void getWeatherData(String city, final GetWeatherDataCallback dataCallback) {
        weatherApiService.getCurrentByCity(city, lang, KEY)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful()) {
                            CurrentWeather currentWeather = response.body();
                            if (currentWeather != null) {
                                weatherRepository.setCurrentWeather(currentWeather);
                                dataCallback.onCurrentSuccess(weatherRepository.getCurrentWeather());
                                Log.d(MY_TAG, " current done");
                            } else {
                                Toast.makeText(App.getInstance().getApplicationContext(), "Wrong city name", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Log.d(MY_TAG, "current failure");
                        t.printStackTrace();
                        dataCallback.onError(App.getInstance().getString(R.string.network_error));
                    }
                });

        weatherApiService.getForecastByCity(city, lang, DAYS, KEY)
                .enqueue(new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                        if (response.isSuccessful()) {
                            Forecast forecast = response.body();
                            if(forecast != null) {
                                weatherRepository.setForecast(forecast);
                                dataCallback.onForecastSuccess(weatherRepository.getForecast());
                                Log.d(MY_TAG, "forecast done");
                            } else {
                                Toast.makeText(App.getInstance().getApplicationContext(), "Wrong city name", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Forecast> call, Throwable t) {
                        Log.d(MY_TAG, "forecast failure");
                        //dataCallback.onError(App.getInstance().getString(R.string.network_error));
                    }
                });
    }

    public interface GetWeatherDataCallback {
        void onCurrentSuccess(CurrentWeather currentWeather);

        void onForecastSuccess(Forecast forecast);

        void onError(String error);
    }
}
