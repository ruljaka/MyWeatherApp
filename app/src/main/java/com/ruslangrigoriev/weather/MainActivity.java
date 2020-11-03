package com.ruslangrigoriev.weather;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.robinhood.spark.SparkView;
import com.ruslangrigoriev.weather.Util.DataTask;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.adapter.DailyAdapter;
import com.ruslangrigoriev.weather.adapter.GraphAdapter;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.DataItem;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.model.ForecastDataItem;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyEventListener {

    public static final String MY_TAG = "MyTag";
    private String lang;

    private String city;

    private TextView cityNameTV;
    private TextView currentTempTV;
    private TextView currentWeatherTV;
    private TextView currentMaxMinTempTV;
    private GridView dailyGV;
    private ConstraintLayout mainCL;
    private ConstraintLayout secondaryCL;
    private ImageView mainIV;
    private TextView windDirTV;
    private TextView preProTV;
    private TextView windSpdTV;
    private TextView humidityTV;
    private TextView uvTV;
    private TextView preTV;
    private TextView perTempTV;
    private TextView visibilityTV;
    private TextView pressureTV;

    private Dialog locationDialog;
    private Button dialogOkBtn;
    private EditText dialogCityNameET;
    private ConstraintLayout dialogCL;

    private SharedPreferences sPref;
    private DataTask dataTask;

    private Forecast forecast;
    private CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityNameTV = findViewById(R.id.cityNameTV);
        currentTempTV = findViewById(R.id.currentTempTV);
        currentWeatherTV = findViewById(R.id.currentWeatherTV);
        currentMaxMinTempTV = findViewById(R.id.currentMaxMinTempTV);

        dailyGV = findViewById(R.id.dailyGV);
        mainCL = findViewById(R.id.main_CL);
        secondaryCL = findViewById(R.id.secondary_CL);
        mainIV = findViewById(R.id.main_image);

        windDirTV = findViewById(R.id.windDirTV);
        windSpdTV = findViewById(R.id.windSpdTV);
        preProTV = findViewById(R.id.preProTV);
        humidityTV = findViewById(R.id.humidityTV);
        uvTV = findViewById(R.id.uvTV);
        preTV = findViewById(R.id.preTV);
        perTempTV = findViewById(R.id.perTempTV);
        visibilityTV = findViewById(R.id.visibilityTV);
        pressureTV = findViewById(R.id.pressureTV);

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
            //save city in preference
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
        if (currentWeather != null) {
            setCurrentWeather();
            setDetailsCurrent();
            setDayNight();
            Util.getInstance().saveCity(sPref, city);
        } else {
            Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
        }

        if (forecast != null) {
            ForecastDataItem currentForecastDataItem = forecast.getData().get(0);
            //setup min/max current
            String min = String.valueOf(Math.round(currentForecastDataItem.getMinTemp()));
            String max = String.valueOf(Math.round(currentForecastDataItem.getMaxTemp()));
            currentMaxMinTempTV.setText(String.format("%s / %s°C", max, min));
            setGridView();
            setGraphView();
        } else {
            Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestFailed() {
        Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
    }

    private void setGraphView() {
        //setup GraphView
        SparkView sparkView = findViewById(R.id.sparkView);
        float[] graphData = new float[8];
        for (int i = 1; i < forecast.getData().size() + 1; i++) {
            graphData[i] = (float) forecast.getData().get(i - 1).getMaxTemp();
        }
        graphData[0] = (float) forecast.getData().get(0).getMaxTemp();
        graphData[7] = (float) forecast.getData().get(5).getMaxTemp();
        sparkView.setAdapter(new GraphAdapter(graphData));
    }

    private void setGridView() {
        //setup dailyGridView
        dailyGV.setNumColumns(6);
        //disable scrolling gridView
        dailyGV.setOnTouchListener((v, event) -> event.getAction() == MotionEvent.ACTION_MOVE);
        DailyAdapter dailyAdapterGridView = new DailyAdapter(getApplicationContext(), (ArrayList<ForecastDataItem>) forecast.getData());
        dailyGV.setAdapter(dailyAdapterGridView);
    }

    //setup current weather
    private void setCurrentWeather() {
        cityNameTV.setText(currentWeather.getData().get(0).getCityName());
        DataItem currentData = currentWeather.getData().get(0);
        currentTempTV.setText(String.format(Locale.getDefault(), "%d°", Math.round(currentData.getTemp())));
        currentWeatherTV.setText(currentData.getWeather().getDescription());
    }

    private void setDetailsCurrent() {
        preProTV.setText(String.format(Locale.getDefault(), "%d%%", forecast.getData().get(0).getPop()));
        preTV.setText(String.format(Locale.getDefault(), "%.2f %s", currentWeather.getData().get(0).getPrecip(), getString(R.string.mm)));
        windDirTV.setText(String.format(Locale.getDefault(), "%s %s", currentWeather.getData().get(0).getWindCdir(), getString(R.string.wind)));
        windSpdTV.setText(String.format(Locale.getDefault(), "%.1f %s", currentWeather.getData().get(0).getWindSpd(), getString(R.string.m_s)));
        perTempTV.setText(String.format(Locale.getDefault(), "%d°C", Math.round(currentWeather.getData().get(0).getAppTemp())));
        humidityTV.setText(String.format(Locale.getDefault(), "%d%%", Math.round(currentWeather.getData().get(0).getRh())));
        visibilityTV.setText(String.format(Locale.getDefault(), "%.1f %s", currentWeather.getData().get(0).getVis(), getString(R.string.km)));
        uvTV.setText(String.format(Locale.getDefault(), "%.1f", currentWeather.getData().get(0).getUv()));
        pressureTV.setText(String.format(Locale.getDefault(), "%d %s", (int) currentWeather.getData().get(0).getPres(), getString(R.string.mb)));
    }

    //set Day/Night
    private void setDayNight() {
        if (!Util.getInstance().isDay(currentWeather)) {
            mainCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_night_background));
            mainIV.setImageResource(R.drawable.ic_nigh_timage);
            secondaryCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gradient_secondary_night));
            dialogCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_night_button));
        } else {
            mainCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_day_background));
            mainIV.setImageResource(R.drawable.ic_day_image);
            secondaryCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gradient_secondary_day));
            dialogCL.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dialog_day_button));
        }
    }
}