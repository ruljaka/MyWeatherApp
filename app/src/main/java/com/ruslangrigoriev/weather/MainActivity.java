package com.ruslangrigoriev.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ruslangrigoriev.weather.Util.DataTask;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.fragments.BackFragment;
import com.ruslangrigoriev.weather.fragments.EnterCityDialog;
import com.ruslangrigoriev.weather.fragments.HeadFragment;
import com.ruslangrigoriev.weather.fragments.SwipeFragment;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.Forecast;

public class MainActivity extends AppCompatActivity implements DataTask.DataListener, EnterCityDialog.DialogListener {

    private String lang;
    private String city;
    private boolean isDay = true;
    private EnterCityDialog locationDialog;
    private SharedPreferences sPref;
    private DataTask dataTask;
    private Forecast forecast;
    private CurrentWeather currentWeather;
    private SwipeFragment swipeFragment;
    private BackFragment backFragment;
    private HeadFragment headFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headFragment = HeadFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.head_fragment, headFragment, HeadFragment.HEAD_TAG)
                .commit();
        backFragment = BackFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.back_fragment, backFragment, BackFragment.BACK_TAG)
                .commit();
        swipeFragment = SwipeFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.swipe_fragment, swipeFragment, SwipeFragment.SWIPE_TAG)
                .commit();

        //set language
        lang = getResources().getConfiguration().locale.getLanguage();

        //load city from preference
        sPref = getPreferences(MODE_PRIVATE);
        city = Util.getInstance().loadCity(sPref);

        // if city is empty
        // call method
        if (city.equals("")) {
            getCityName(findViewById(R.id.locationImBtn));
        } else {
            //update weather
            getWeather();
        }
    }

    //call dialog for enter city
    public void getCityName(View view) {
        locationDialog = new EnterCityDialog(isDay);
        locationDialog.show(getSupportFragmentManager(), "showDialog");
    }

    @Override
    public void onDialogOkBtnClick(String cityName) {
        if (cityName.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.enter_city_name, Toast.LENGTH_SHORT).show();
        } else {
            //update weather
            city = cityName;
            getWeather();
            locationDialog.dismiss();
        }
    }

    public void getWeather() {
        //get API data
        dataTask = new DataTask(this, city, lang);
        dataTask.execute();
    }

    // bind UI
    @Override
    public void onApiDataReceived() {
        currentWeather = dataTask.getCurrentWeather();
        forecast = dataTask.getForecast();
        if (currentWeather != null && forecast != null) {
            headFragment.setCurrentWeather(currentWeather, forecast);
            swipeFragment.setDetailsCurrent(forecast, currentWeather);
            swipeFragment.setGridView(this, forecast);
            swipeFragment.setGraphView(forecast);
            setDayNight();
            Util.getInstance().saveCity(sPref, city);
        } else {
            Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestFailed() {
        Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
    }

    private void setDayNight() {
        if (!Util.getInstance().isDay(currentWeather)) {
            isDay = false;
            backFragment.setNight(this);
            swipeFragment.setNight(this);
        } else {
            isDay = true;
            backFragment.setDay(this);
            swipeFragment.setDay(this);
        }
    }


}