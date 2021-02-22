package com.ruslangrigoriev.weather.data.entities;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("icon")
    private String icon;

    @SerializedName("description")
    private String description;

    public Weather(String icon, String description) {
        this.icon = icon;
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}