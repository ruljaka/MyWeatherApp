package com.ruslangrigoriev.weather;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.ruslangrigoriev.weather.Util.DataTask;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.fragments.BackFragment;
import com.ruslangrigoriev.weather.fragments.HeadFragment;
import com.ruslangrigoriev.weather.fragments.SwipeFragment;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.DataItem;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.model.ForecastDataItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyEventListener {

    private String lang;
    private String city;

    private Dialog locationDialog;
    private Button dialogOkBtn;
    private EditText dialogCityNameET;
    private ConstraintLayout dialogCL;

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

        //binding Dialog
        locationDialog = new Dialog(this);
        locationDialog.setContentView(R.layout.dialog_enter_city);
        locationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCL = locationDialog.findViewById(R.id.dialog_CL);
        dialogCityNameET = locationDialog.findViewById(R.id.dialogCityNameET);
        dialogOkBtn = locationDialog.findViewById(R.id.okBtn);

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
        dialogOkBtn.setOnClickListener(v -> {
            if (dialogCityNameET.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.enter_city_name, Toast.LENGTH_SHORT).show();
            } else {
                //update weather
                city = dialogCityNameET.getText().toString();
                getWeather();
                locationDialog.dismiss();
            }
        });
        locationDialog.show();
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
        if (currentWeather != null && forecast !=null) {
            headFragment.setCurrentWeather(currentWeather,forecast);
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
            backFragment.setNight(this);
            swipeFragment.setNight(this);
            dialogCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_button));
        } else {
            backFragment.setDay(this);
            swipeFragment.setDay(this);
            dialogCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_button));
        }
    }
}