package com.ruslangrigoriev.weather.DI;

import android.app.Application;

import com.ruslangrigoriev.weather.domain.WeatherDAO;
import com.ruslangrigoriev.weather.domain.WeatherDataBase;
import com.ruslangrigoriev.weather.domain.WeatherDataSource;
import com.ruslangrigoriev.weather.domain.WeatherRepository;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;
import com.ruslangrigoriev.weather.widget.WeatherWidgetProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(WeatherDataViewModel weatherDataViewModel);

    void inject(WeatherWidgetProvider weatherWidgetProvider);

    void inject(WeatherDataSource weatherDataSource);

    Application application();

    WeatherDAO weatherDAO();

    WeatherDataBase weatherDataBase();

    WeatherRepository weatherRepository();


}
