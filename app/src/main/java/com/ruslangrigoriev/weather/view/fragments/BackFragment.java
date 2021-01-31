package com.ruslangrigoriev.weather.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;

public class BackFragment extends Fragment {
    public static final String BACK_TAG = "BackFragment";

    private ImageView backIV;
    private ConstraintLayout backCL;
    public WeatherDataViewModel weatherDataViewModel;

    public BackFragment() {
    }

    public static BackFragment newInstance() {
        return new BackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_back, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backIV = view.findViewById(R.id.back_image);
        backCL = view.findViewById(R.id.back_CL);

        weatherDataViewModel = new ViewModelProvider(getActivity()).get(WeatherDataViewModel.class);
        weatherDataViewModel.getCurrent().observe(getViewLifecycleOwner(), currentWeather -> {
            if (Util.getInstance().isDay(currentWeather)) {
                backCL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_day_background));
                backIV.setImageResource(R.drawable.ic_day_image);
            } else {
                backCL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_night_background));
                backIV.setImageResource(R.drawable.ic_nigh_timage);
            }
        });
    }
}