package com.ruslangrigoriev.weather;

import android.app.Application;

import androidx.room.Room;

import com.ruslangrigoriev.weather.domain.WeatherApiService;
import com.ruslangrigoriev.weather.domain.WeatherDAO;
import com.ruslangrigoriev.weather.domain.WeatherDataBase;
import com.ruslangrigoriev.weather.domain.WeatherRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    private static final int NUMBER_OF_THREADS = 4;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public WeatherApiService weatherApiService;
    public WeatherDAO weatherDAO;
    public WeatherDataBase weatherDataBase;
    public WeatherRepository weatherRepository;
    public final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRoom();
        initRetrofit();
        this.weatherRepository = new WeatherRepository();
    }

    private void initRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        weatherApiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherApiService.class);
    }

    private void initRoom() {
        weatherDataBase = Room.databaseBuilder(getApplicationContext(),
                WeatherDataBase.class, "weather_database")
                .fallbackToDestructiveMigration()
                .build();
        weatherDAO = weatherDataBase.getWeatherDAO();
    }

    public WeatherDAO getWeatherDAO() {
        return weatherDAO;
    }

    public WeatherRepository getWeatherRepository() {
        return weatherRepository;
    }
}
