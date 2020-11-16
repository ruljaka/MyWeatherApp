package com.ruslangrigoriev.weather.fragments;

import android.content.Context;
import android.os.Bundle;
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

import com.robinhood.spark.SparkView;
import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.adapter.DailyAdapter;
import com.ruslangrigoriev.weather.adapter.GraphAdapter;
import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.model.ForecastDataItem;

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

    public SwipeFragment() {
    }

    public static SwipeFragment newInstance() {
        return new SwipeFragment();
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
    }

    public void setDetailsCurrent(Forecast forecast, CurrentWeather currentWeather) {
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

    public void setGridView(Context context, Forecast forecast) {
        dailyGV.setNumColumns(6);
        //disable scrolling gridView
        dailyGV.setOnTouchListener((v, event) -> event.getAction() == MotionEvent.ACTION_MOVE);
        DailyAdapter dailyAdapterGridView = new DailyAdapter(context, (ArrayList<ForecastDataItem>) forecast.getData());
        dailyGV.setAdapter(dailyAdapterGridView);
    }

    public void setGraphView(Forecast forecast) {
        float[] graphData = new float[8];
        for (int i = 1; i < forecast.getData().size() + 1; i++) {
            graphData[i] = (float) forecast.getData().get(i - 1).getMaxTemp();
        }
        graphData[0] = (float) forecast.getData().get(0).getMaxTemp();
        graphData[7] = (float) forecast.getData().get(5).getMaxTemp();
        sparkView.setAdapter(new GraphAdapter(graphData));
    }

    public void setDay(Context context){
        swipeCL.setBackground(ContextCompat.getDrawable(context, R.drawable.gradient_secondary_day));
    }

    public void setNight(Context context){
        swipeCL.setBackground(ContextCompat.getDrawable(context, R.drawable.gradient_secondary_night));
    }
}