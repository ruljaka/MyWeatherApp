package com.ruslangrigoriev.weather.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.robinhood.spark.SparkView;
import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.ForecastDataItem;
import com.ruslangrigoriev.weather.view.adapters.DailyAdapter;
import com.ruslangrigoriev.weather.view.adapters.GraphAdapter;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class SwipeFragment extends Fragment {

    public static final String SWIPE_TAG = "SwipeFragment";

    private GridView dailyGV;
    private ConstraintLayout swipeCL;
    private TextView windDirTV;
    private TextView preProTV;
    private TextView windSpdTV;
    private TextView humidityTV;
    private TextView uvTV;
    private TextView preTV;
    private TextView perTempTV;
    private TextView visibilityTV;
    private TextView pressureTV;
    private SparkView sparkView;
    public WeatherDataViewModel weatherDataViewModel;

    public SwipeFragment(WeatherDataViewModel weatherDataViewModel) {
        this.weatherDataViewModel = weatherDataViewModel;
    }

    public static SwipeFragment newInstance(WeatherDataViewModel weatherDataViewModel) {
        return new SwipeFragment(weatherDataViewModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_swipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailyGV = view.findViewById(R.id.dailyGV);
        swipeCL = view.findViewById(R.id.swipe_CL);
        windDirTV = view.findViewById(R.id.windDirTV);
        windSpdTV = view.findViewById(R.id.windSpdTV);
        preProTV = view.findViewById(R.id.preProTV);
        humidityTV = view.findViewById(R.id.humidityTV);
        uvTV = view.findViewById(R.id.uvTV);
        preTV = view.findViewById(R.id.preTV);
        perTempTV = view.findViewById(R.id.perTempTV);
        visibilityTV = view.findViewById(R.id.visibilityTV);
        pressureTV = view.findViewById(R.id.pressureTV);
        sparkView = view.findViewById(R.id.sparkView);

        weatherDataViewModel.getCurrent().observe(getViewLifecycleOwner(), new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                preTV.setText(String.format(Locale.getDefault()
                        , "%.2f %s"
                        , currentWeather.getData().get(0).getPrecip()
                        , SwipeFragment.this.getString(R.string.mm)));
                windDirTV.setText(String.format(Locale.getDefault()
                        , "%s %s"
                        , currentWeather.getData().get(0).getWindCdir()
                        , SwipeFragment.this.getString(R.string.wind)));
                windSpdTV.setText(String.format(Locale.getDefault(),
                        "%.1f %s",
                        currentWeather.getData().get(0).getWindSpd(),
                        SwipeFragment.this.getString(R.string.m_s)));
                perTempTV.setText(String.format(Locale.getDefault(),
                        "%d°C",
                        Math.round(currentWeather.getData().get(0).getAppTemp())));
                humidityTV.setText(String.format(Locale.getDefault(),
                        "%d%%",
                        Math.round(currentWeather.getData().get(0).getRh())));
                visibilityTV.setText(String.format(Locale.getDefault(),
                        "%.1f %s",
                        currentWeather.getData().get(0).getVis(),
                        SwipeFragment.this.getString(R.string.km)));
                uvTV.setText(String.format(Locale.getDefault(),
                        "%.1f",
                        currentWeather.getData().get(0).getUv()));
                pressureTV.setText(String.format(Locale.getDefault(),
                        "%d %s",
                        (int) currentWeather.getData().get(0).getPres(),
                        SwipeFragment.this.getString(R.string.mb)));
                if (Util.getInstance().isDay(currentWeather)) {
                    swipeCL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_secondary_day));
                } else {
                    swipeCL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_secondary_night));
                }
            }
        });

        weatherDataViewModel.getForecast().observe(getViewLifecycleOwner(), forecast -> {
            preProTV.setText(String.format(Locale.getDefault(),
                    "%d%%",
                    forecast.getData().get(0).getPop()));
            //set dailyGridView
            dailyGV.setNumColumns(6);
            dailyGV.setEnabled(false);
            DailyAdapter dailyAdapterGridView = new DailyAdapter(getContext(), (ArrayList<ForecastDataItem>) forecast.getData());
            dailyGV.setAdapter(dailyAdapterGridView);
            //set GraphView
            float[] graphData = new float[8];
            for (int i = 1; i < forecast.getData().size() + 1; i++) {
                graphData[i] = (float) forecast.getData().get(i - 1).getMaxTemp();
            }
            graphData[0] = (float) forecast.getData().get(0).getMaxTemp();
            graphData[7] = (float) forecast.getData().get(5).getMaxTemp();
            sparkView.setAdapter(new GraphAdapter(graphData));
        });
    }
}