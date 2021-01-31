package com.ruslangrigoriev.weather;

import android.app.Application;

import com.ruslangrigoriev.weather.data.WeatherApiService;
import com.ruslangrigoriev.weather.data.WeatherRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App instance;
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    public static final String MY_TAG = "MyTag";

    public WeatherApiService weatherApiService;
    //public WeatherApiInteractor weatherApiInteractor;
    public WeatherRepository weatherRepository = new WeatherRepository();

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        //Log.d(MY_TAG,"App onCreate");
        super.onCreate();
        instance = this;
        initRetrofit();
        //initInteractor();
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

    /*private void initInteractor() {
        weatherApiInteractor = new WeatherApiInteractor(weatherApiService, weatherRepository);
    }*/
}
