package com.ruslangrigoriev.weather.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ruslangrigoriev.weather.MyEventListener;
import com.ruslangrigoriev.weather.model.CurrentWeather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Util {
    public static final String MY_TAG = "MyTag";
    private final String SAVED_CITY = "saved_city";
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

        ZoneId systemZoneId = ZoneId.of(currentWeather.getData().get(0).getTimezone());
        ZonedDateTime systemZDTSunset = londonZDTSunset.withZoneSameInstant(systemZoneId);
        ZonedDateTime systemZDTSunrise = londonZDTSunrise.withZoneSameInstant(systemZoneId);
        ZonedDateTime nowZDT = ZonedDateTime.now().withZoneSameInstant(systemZoneId);

        return systemZDTSunset.isAfter(nowZDT) && systemZDTSunrise.isBefore(nowZDT);
    }

    public String loadCity(SharedPreferences sPref) {
       return sPref.getString(SAVED_CITY, "");
    }

    public void saveCity(SharedPreferences sPref, String city) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_CITY, city);
        ed.apply();
    }

}
