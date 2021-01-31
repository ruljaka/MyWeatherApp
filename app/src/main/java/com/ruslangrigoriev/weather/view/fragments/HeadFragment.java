package com.ruslangrigoriev.weather.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;
import com.ruslangrigoriev.weather.data.entities.DataItem;
import com.ruslangrigoriev.weather.data.entities.Forecast;
import com.ruslangrigoriev.weather.data.entities.ForecastDataItem;
import com.ruslangrigoriev.weather.view.EnterCityDialog;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;

import java.util.Locale;

public class HeadFragment extends Fragment {

    public static final String HEAD_TAG = "HeadFragment";
    private TextView cityNameTV;
    private TextView currentTempTV;
    private TextView currentWeatherTV;
    private TextView currentMaxMinTempTV;
    private ImageButton locationIB;
    private EnterCityDialog locationDialog;
    public WeatherDataViewModel weatherDataViewModel;
    boolean isDay = true;

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
        locationIB = view.findViewById(R.id.locationImBtn);

        weatherDataViewModel = new ViewModelProvider(getActivity()).get(WeatherDataViewModel.class);

        weatherDataViewModel.getCurrent().observe(getViewLifecycleOwner(), (CurrentWeather currentWeather) -> {
            isDay = Util.getInstance().isDay(currentWeather);
            cityNameTV.setText(currentWeather.getData().get(0).getCityName());
            DataItem currentData = currentWeather.getData().get(0);
            currentTempTV.setText(String.format(Locale.getDefault(), "%d°", Math.round(currentData.getTemp())));
            currentWeatherTV.setText(currentData.getWeather().getDescription());
        });

        weatherDataViewModel.getForecast().observe(getViewLifecycleOwner(), (Forecast forecast) -> {
            ForecastDataItem currentForecastDataItem = forecast.getData().get(0);
            String min = String.valueOf(Math.round(currentForecastDataItem.getMinTemp()));
            String max = String.valueOf(Math.round(currentForecastDataItem.getMaxTemp()));
            currentMaxMinTempTV.setText(String.format("%s / %s°C", max, min));
        });

        weatherDataViewModel.getError().observe(getViewLifecycleOwner(), error -> Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show());

        locationIB.setOnClickListener(v -> {
            locationDialog = new EnterCityDialog(isDay);
            locationDialog.show(getActivity().getSupportFragmentManager(), "showDialog");
        });
    }
}