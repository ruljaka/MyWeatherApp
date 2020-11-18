package com.ruslangrigoriev.weather.view;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.view.fragments.BackFragment;
import com.ruslangrigoriev.weather.view.fragments.HeadFragment;
import com.ruslangrigoriev.weather.view.fragments.SwipeFragment;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;

public class MainActivity extends AppCompatActivity implements EnterCityDialog.DialogListener {

    private EnterCityDialog locationDialog;
    private SwipeFragment swipeFragment;
    private BackFragment backFragment;
    private HeadFragment headFragment;
    public WeatherDataViewModel weatherDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataViewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);

        headFragment = HeadFragment.newInstance(weatherDataViewModel);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.head_fragment, headFragment, HeadFragment.HEAD_TAG)
                .commit();
        backFragment = BackFragment.newInstance(weatherDataViewModel);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.back_fragment, backFragment, BackFragment.BACK_TAG)
                .commit();
        swipeFragment = SwipeFragment.newInstance(weatherDataViewModel);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.swipe_fragment, swipeFragment, SwipeFragment.SWIPE_TAG)
                .commit();

        if (Util.getInstance().getCityName().equals("")) {
            locationDialog = new EnterCityDialog(true);
            locationDialog.show(getSupportFragmentManager(), "showDialog");
        } else {
            weatherDataViewModel.getData(Util.getInstance().getCityName());
        }
    }

    @Override
    public void onDialogOkBtnClick(String cityName, Dialog dialog) {
        if (cityName.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.enter_city_name, Toast.LENGTH_SHORT).show();
        } else {
            weatherDataViewModel.getData(cityName);
            dialog.dismiss();
        }
    }
}