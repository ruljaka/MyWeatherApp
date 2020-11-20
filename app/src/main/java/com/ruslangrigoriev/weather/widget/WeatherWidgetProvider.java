package com.ruslangrigoriev.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.Forecast;
import com.ruslangrigoriev.weather.domain.WeatherApiInteractor;
import com.ruslangrigoriev.weather.view.MainActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WeatherWidgetProvider extends AppWidgetProvider {

    public static final String MY_TAG = "MyTag";
    private static final String UPDATE_ALL_WIDGETS = "update_all_widgets";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(MY_TAG,"widget onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(MY_TAG,"widget onUpdate");
        if(!Util.getInstance().getCityName().equals("")){
            for (int widgetId : appWidgetIds) {
                updateWidgetFromAPI(context, appWidgetManager, widgetId);
            }
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(MY_TAG,"widget onReceive");
        if (intent.getAction().equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(
                    context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int widgetId : ids) {
                updateWidgetFromRepo(context, appWidgetManager, widgetId);
            }
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

        App.getInstance().weatherApiInteractor.getWeatherData(Util.getInstance().getCityName(),
                new WeatherApiInteractor.GetWeatherDataCallback() {
                    @Override
                    public void onCurrentSuccess(CurrentWeather currentWeather) {
                        bindWidgetViews(currentWeather, remoteViews, appWidgetManager, widgetId);
                        Log.d(MY_TAG, "updateWidget from API");
                    }

                    @Override
                    public void onForecastSuccess(Forecast forecast) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.d(MY_TAG, "updateWidget from API  error");
                    }
                });
    }

    private void updateWidgetFromRepo(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_widget);
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        mainIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pIntent = PendingIntent.getActivity(context, widgetId,
                mainIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widgetLL, pIntent);
        CurrentWeather currentWeather = App.getInstance().weatherRepository.getCurrentWeather();
        bindWidgetViews(currentWeather, remoteViews, appWidgetManager, widgetId);
        Log.d(MY_TAG, "updateWidget from Repo");
    }

    private void bindWidgetViews(CurrentWeather currentWeather, RemoteViews remoteViews, AppWidgetManager appWidgetManager, int widgetId) {
        DateTimeFormatter fIn = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH",
                Locale.getDefault());
        LocalDate localDate = LocalDate.parse(currentWeather.getData().get(0).getDatetime(),
                fIn);
        //set date TextView
        DateTimeFormatter dateOut = DateTimeFormatter.ofPattern("EEE, MMM dd",
                Locale.getDefault());
        String outputDate = localDate.format(dateOut);
        remoteViews.setTextViewText(R.id.widgetDateTV, outputDate);
        String weatherDescription = currentWeather.getData().get(0).getWeather().getDescription();
        remoteViews.setTextViewText(R.id.widgetWeatherDescriptionTV, weatherDescription);
        String weatherTemp = String.valueOf(Math.round(currentWeather.getData().get(0).getTemp()));
        remoteViews.setTextViewText(R.id.widgetTempTV, weatherTemp + "°");

        //update
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }


}
