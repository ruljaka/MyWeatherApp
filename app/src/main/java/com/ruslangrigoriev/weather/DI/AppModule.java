package com.ruslangrigoriev.weather.DI;

import android.app.Application;

import com.ruslangrigoriev.weather.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }
}
