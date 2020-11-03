package com.ruslangrigoriev.weather.Util;

import android.util.Log;

import com.ruslangrigoriev.weather.model.CurrentWeather;
import com.ruslangrigoriev.weather.model.Forecast;
import com.ruslangrigoriev.weather.network.NetworkService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Util {
    public static final String MY_TAG = "MyTag";
    private static Util instance;

    public Util() {
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public boolean isDay(CurrentWeather currentWeather) {
        LocalTime timeSunset = LocalTime.parse(currentWeather.getData().get(0).getSunset());
        LocalTime timeSunrise = LocalTime.parse(currentWeather.getData().get(0).getSunrise());
        LocalDate currentDate = LocalDate.now();

        LocalDateTime dateTimeSunset = LocalDateTime.of(currentDate, timeSunset);
        LocalDateTime dateTimeSunrise = LocalDateTime.of(currentDate, timeSunrise);

        ZoneId LondonZoneId = ZoneId.of("Europe/London");
        ZonedDateTime londonZDTSunset = dateTimeSunset.atZone(LondonZoneId);
        ZonedDateTime londonZDTSunrise = dateTimeSunrise.atZone(LondonZoneId);
        Log.d(MY_TAG,londonZDTSunrise.toString()+ "sunrise");
        Log.d(MY_TAG,londonZDTSunset.toString() + "sunset\n");


        ZoneId systemZoneId = ZoneId.of(currentWeather.getData().get(0).getTimezone());
        ZonedDateTime systemZDTSunset = londonZDTSunset.withZoneSameInstant(systemZoneId);
        ZonedDateTime systemZDTSunrise = londonZDTSunrise.withZoneSameInstant(systemZoneId);
        ZonedDateTime nowZDT = ZonedDateTime.now().withZoneSameInstant(systemZoneId);
        Log.d(MY_TAG,systemZDTSunrise.toString() + "sunrise");
        Log.d(MY_TAG,systemZDTSunset.toString()+ "sunset\n");

        Log.d(MY_TAG,nowZDT.toString()+ "now");

        return systemZDTSunset.isAfter(nowZDT) && systemZDTSunrise.isBefore(nowZDT);
    }
}
