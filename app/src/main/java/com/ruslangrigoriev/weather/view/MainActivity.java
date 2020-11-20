package com.ruslangrigoriev.weather.view;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.Util.Util;
import com.ruslangrigoriev.weather.view.fragments.BackFragment;
import com.ruslangrigoriev.weather.view.fragments.HeadFragment;
import com.ruslangrigoriev.weather.view.fragments.SwipeFragment;
import com.ruslangrigoriev.weather.viewmodel.WeatherDataViewModel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity implements EnterCityDialog.DialogListener {

    public static final String MY_TAG = "MyTag";

    private SwipeRefreshLayout swipeRefreshLayout;
    private SlidingUpPanelLayout slidingPL;
    private EnterCityDialog locationDialog;
    private SwipeFragment swipeFragment;
    private BackFragment backFragment;
    private HeadFragment headFragment;
    public WeatherDataViewModel weatherDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataViewModel = new ViewModelProvider(this)
                .get(WeatherDataViewModel.class);

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

        //if city is not set show dialog to enter city
        if (Util.getInstance().getCityName().equals("")) {
            locationDialog = new EnterCityDialog(true);
            locationDialog.show(getSupportFragmentManager(), "showDialog");
        }

        //subscribe ViewModel to auto update
        getLifecycle().addObserver(weatherDataViewModel);

        //swipe down to refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            weatherDataViewModel.getData(Util.getInstance().getCityName());
            swipeRefreshLayout.setRefreshing(false);
            Log.d(MY_TAG, "refreshed");
        });

        //sliding panel state listener
        slidingPL = findViewById(R.id.sliding_layout);
        slidingPL.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    swipeRefreshLayout.setEnabled(false);
                    swipeFragment.upArrow.setImageResource(R.drawable.ic_down_arrow);
                } else {
                    swipeRefreshLayout.setEnabled(true);
                    swipeFragment.upArrow.setImageResource(R.drawable.ic_up_arrow);
                }
            }
        });
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