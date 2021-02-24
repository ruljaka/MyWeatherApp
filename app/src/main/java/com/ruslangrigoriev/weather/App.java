package com.ruslangrigoriev.weather;

import android.app.Application;

import com.ruslangrigoriev.weather.DI.AppComponent;
import com.ruslangrigoriev.weather.DI.AppModule;
import com.ruslangrigoriev.weather.DI.DaggerAppComponent;
import com.ruslangrigoriev.weather.DI.NetworkModule;
import com.ruslangrigoriev.weather.DI.RoomModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private AppComponent appComponent;

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .networkModule(new NetworkModule(BASE_URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
