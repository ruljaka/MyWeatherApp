package com.ruslangrigoriev.weather.viewmodel;

import android.content.Intent;
import android.content.res.Resources;
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
import com.ruslangrigoriev.weather.domain.WeatherRepository;
import com.ruslangrigoriev.weather.data.entities.AllWeatherData;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastWeather;
import com.ruslangrigoriev.weather.widget.WeatherWidgetProvider;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class WeatherDataViewModel extends ViewModel implements LifecycleObserver {
    private static final int DELAY = 300000;
    public static final String MY_TAG = "MyTag";
    private static final String UPDATE_ALL_WIDGETS = "update_all_widgets";
    private final static String DAYS = "6";
    private final static String KEY = "5a7d8bef29c34458bc0f3e60f0cbefcd";
    String lang = Resources.getSystem().getConfiguration().locale.getLanguage();


    private final WeatherRepository weatherRepository = App.getInstance().getWeatherRepository();

    private final LiveData<CurrentWeather> currentLiveData;
    private final LiveData<ForecastWeather> forecastLiveData;
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();


    public WeatherDataViewModel() {
        super();
        currentLiveData = weatherRepository.getCurrentWeather();
        forecastLiveData = weatherRepository.getForecastWeather();
    }

    Disposable disposable;

    private final Handler handler = new Handler();

    private final Runnable taskRunnable = new Runnable() {
        @Override
        public void run() {
            getData(Util.getInstance().getCityName());
            handler.postDelayed(taskRunnable, DELAY);
        }
    };

    public LiveData<CurrentWeather> getCurrent() {
        return currentLiveData;
    }

    public LiveData<ForecastWeather> getForecast() {
        return forecastLiveData;
    }

    public MutableLiveData<String> getError() {
        return errorLiveData;
    }

    public void getData(String cityName) {
        Single<Response<CurrentWeather>> currentWeatherSingle = App.getInstance()
                .weatherApiService
                .getCurrentByCity(cityName, lang, KEY)
                .doOnSuccess(currentWeather -> Log.d(MY_TAG, "doOnSuccess :: currentWeatherSingle : code " + currentWeather.code()))
                .doOnError(throwable -> Log.d(MY_TAG, "onError :: currentWeatherSingle : " + throwable.getMessage()));

        Single<Response<ForecastWeather>> forecastSingle = App.getInstance()
                .weatherApiService
                .getForecastByCity(cityName, lang, DAYS, KEY)
                .doOnSuccess(forecast -> Log.d(MY_TAG, "doOnSuccess :: forecast : code " + forecast.code()))
                .doOnError(throwable -> Log.d(MY_TAG, "onError :: forecast : " + throwable.getMessage()));

        disposable = Single.zip(currentWeatherSingle, forecastSingle
                , (currentWeatherResponse, forecastResponse) -> {
                    if(currentWeatherResponse.code() != 200){
                        Log.d(MY_TAG,"code() != 200");
                        return null;
                    }
                    return new AllWeatherData(currentWeatherResponse.body(), forecastResponse.body());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(allWeatherData -> Log.d(MY_TAG, "ZIP :: allWeatherData"))
                .subscribe((AllWeatherData allWeatherData) -> {
                            if (allWeatherData.getCurrentWeather() != null
                                    && allWeatherData.getForecastWeather() != null) {
                                //save data to Repo
                                weatherRepository.insertCurrentWeather(allWeatherData.getCurrentWeather());
                                weatherRepository.insertForecastWeather(allWeatherData.getForecastWeather());

                                //save cityName
                                Util.getInstance().saveCityName(cityName);

                                //update Widget
                                Intent intent = new Intent(App.getInstance(), WeatherWidgetProvider.class);
                                intent.setAction(UPDATE_ALL_WIDGETS);
                                App.getInstance().sendBroadcast(intent);
                                Log.d(MY_TAG, "sendBroadcast :: UPDATE_ALL_WIDGETS");
                            } else {
                                errorLiveData.setValue("Wrong city name");
                            }
                        }
                        , throwable -> {
                            Log.d(MY_TAG, "onError :: ZIP");
                            errorLiveData.setValue("Network Error");
                        });
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLifecycleResume() {
        Log.d(MY_TAG, "onLifecycleResume :: getData ");
        getData(Util.getInstance().getCityName());
        handler.postDelayed(taskRunnable, DELAY);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onLifecyclePaused() {
        handler.removeCallbacksAndMessages(null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onLifecycleDestroy() {
        disposable.dispose();
        Log.d(MY_TAG, "onLifecycleDestroy :: dispose");
    }


}
