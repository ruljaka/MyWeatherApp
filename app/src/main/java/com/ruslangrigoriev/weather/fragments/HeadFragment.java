package com.ruslangrigoriev.weather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.DataItem;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.model.ForecastDataItem;

import java.util.Locale;

public class HeadFragment extends Fragment {

    public static final String HEAD_TAG = "HeadFragment";
    private TextView cityNameTV;
    private TextView currentTempTV;
    private TextView currentWeatherTV;
    private TextView currentMaxMinTempTV;

    public HeadFragment() {
    }

    public static HeadFragment newInstance() {
        return new HeadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_head, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityNameTV = view.findViewById(R.id.cityNameTV);
        currentTempTV = view.findViewById(R.id.currentTempTV);
        currentWeatherTV = view.findViewById(R.id.currentWeatherTV);
        currentMaxMinTempTV = view.findViewById(R.id.currentMaxMinTempTV);
    }

    public void setCurrentWeather(CurrentWeather currentWeather, Forecast forecast) {
        cityNameTV.setText(currentWeather.getData().get(0).getCityName());
        DataItem currentData = currentWeather.getData().get(0);
        currentTempTV.setText(String.format(Locale.getDefault(), "%d°", Math.round(currentData.getTemp())));
        currentWeatherTV.setText(currentData.getWeather().getDescription());
        ForecastDataItem currentForecastDataItem = forecast.getData().get(0);
        String min = String.valueOf(Math.round(currentForecastDataItem.getMinTemp()));
        String max = String.valueOf(Math.round(currentForecastDataItem.getMaxTemp()));
        currentMaxMinTempTV.setText(String.format("%s / %s°C", max, min));
    }
}