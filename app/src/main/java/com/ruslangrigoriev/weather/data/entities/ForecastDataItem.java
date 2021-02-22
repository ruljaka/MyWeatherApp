package com.ruslangrigoriev.weather.data.entities;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;


public class ForecastDataItem {

    @SerializedName("pop")
    private int pop;

    @SerializedName("valid_date")
    private String validDate;

    @SerializedName("min_temp")
    private double minTemp;

    @SerializedName("weather")
    @Embedded
    private Weather weather;


    @SerializedName("max_temp")
    private double maxTemp;


    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
}