package com.ruslangrigoriev.weather.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ruslangrigoriev.weather.App;
import com.ruslangrigoriev.weather.data.entities.CurrentWeather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Util {

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

    public  boolean isDay(CurrentWeather currentWeather) {
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

    public String getCityName() {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVED_CITY, "");
    }

    public void saveCityName(String city) {
        SharedPreferences.Editor ed = App.getInstance().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).edit();
        ed.putString(SAVED_CITY, city);
        ed.apply();
    }

}
