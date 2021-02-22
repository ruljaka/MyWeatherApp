package com.ruslangrigoriev.weather.data.entities;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;


public class DataItem {

    @SerializedName("sunrise")
    private String sunrise;

    @SerializedName("pres")
    private double pres;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("wind_cdir")
    private String windCdir;

    @SerializedName("wind_spd")
    private double windSpd;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("datetime")
    private String datetime;

    @SerializedName("precip")
    private double precip;

    @SerializedName("weather")
    @Embedded
    private Weather weather;

    @SerializedName("vis")
    private double vis;

    @SerializedName("uv")
    private double uv;

    @SerializedName("temp")
    private double temp;

    @SerializedName("app_temp")
    private double appTemp;

    @SerializedName("rh")
    private double rh;

    @SerializedName("sunset")
    private String sunset;


    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public double getPres() {
        return pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(double windSpd) {
        this.windSpd = windSpd;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getVis() {
        return vis;
    }

    public void setVis(double vis) {
        this.vis = vis;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(double appTemp) {
        this.appTemp = appTemp;
    }

    public double getRh() {
        return rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}