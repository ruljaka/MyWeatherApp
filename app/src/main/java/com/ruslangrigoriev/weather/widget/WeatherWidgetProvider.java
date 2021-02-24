package com.ruslangrigoriev.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.lifecycle.LiveData;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.domain.WeatherApiService;
import com.ruslangrigoriev.weather.domain.WeatherRepository;
import com.ruslangrigoriev.weather.view.MainActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class WeatherWidgetProvider extends AppWidgetProvider {

    public static final String MY_TAG = "MyTag";
    private static final String UPDATE_ALL_WIDGETS = "update_all_widgets";
    private final static String KEY = "5a7d8bef29c34458bc0f3e60f0cbefcd";

    @Inject
    WeatherRepository weatherRepository;
    @Inject
    WeatherApiService weatherApiService;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //Log.d(MY_TAG, "widget onUpdate");
        if (!Util.getCityName().equals("")) {
            for (int widgetId : appWidgetIds) {
                updateWidgetFromAPI(context, appWidgetManager, widgetId);
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(MY_TAG, "onReceive :: " + intent.getAction());
        if (intent.getAction().equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(
                    context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int widgetId : ids) {
                updateWidgetFromRepo(context, appWidgetManager, widgetId);
            }
        }
    }

    //from Repo
    private void updateWidgetFromRepo(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_widget);
        //LiveData<CurrentWeather> currentWeather = App.getInstance().weatherRepository.getCurrentWeather();

        App.getInstance().getAppComponent().inject(this);
        LiveData<CurrentWeather> currentWeather = weatherRepository.getCurrentWeather();


        if (currentWeather.getValue() != null) {
            bindWidgetViews(currentWeather.getValue(), remoteViews, appWidgetManager, widgetId);
            Log.d(MY_TAG, "updateWidget from Repo");
        }
    }

    // from API
    private void updateWidgetFromAPI(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_widget);

        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        mainIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pIntent = PendingIntent.getActivity(context, widgetId,
                mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widgetLL, pIntent);

        App.getInstance().getAppComponent().inject(this);
        weatherApiService
                .getCurrentByCity(Util.getCityName()
                        , Resources.getSystem().getConfiguration().locale.getLanguage()
                        , KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<retrofit2.Response<CurrentWeather>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<CurrentWeather> currentWeatherResponse) {
                        if (currentWeatherResponse.body() != null) {
                            bindWidgetViews(currentWeatherResponse.body(), remoteViews, appWidgetManager, widgetId);
                            Log.d(MY_TAG, "updateWidget from API");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //Log.d(MY_TAG, "updateWidget from API  error");
                    }
                });
    }

    private void bindWidgetViews(CurrentWeather currentWeather, RemoteViews remoteViews, AppWidgetManager appWidgetManager, int widgetId) {
        DateTimeFormatter fIn = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH",
                Locale.getDefault());
        LocalDate localDate = LocalDate.parse(currentWeather.getData().get(0).getDatetime(),
                fIn);
        //set date TextView
        DateTimeFormatter dateOut = DateTimeFormatter.ofPattern("EEE, MMM dd",
                Locale.getDefault());
        String outputDate = localDate.format(dateOut).toUpperCase();
        remoteViews.setTextViewText(R.id.widgetDateTV, outputDate);
        String weatherDescription = currentWeather.getData().get(0).getWeather().getDescription();
        remoteViews.setTextViewText(R.id.widgetWeatherDescriptionTV, weatherDescription);
        String weatherTemp = String.valueOf(Math.round(currentWeather.getData().get(0).getTemp()));
        remoteViews.setTextViewText(R.id.widgetTempTV, weatherTemp + "\u00B0");

        //update
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
}
