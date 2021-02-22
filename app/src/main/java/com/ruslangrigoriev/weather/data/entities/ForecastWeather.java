package com.ruslangrigoriev.weather.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ruslangrigoriev.weather.data.ForecastDataItemConverter;

import java.util.List;

@Entity(tableName = "forecastWeather")
public class ForecastWeather {

    @PrimaryKey
    private int id = 1;

    @SerializedName("data")
    @TypeConverters(ForecastDataItemConverter.class)
    private List<ForecastDataItem> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ForecastDataItem> getData() {
        return data;
    }

    public void setData(List<ForecastDataItem> data) {
        this.data = data;
    }
}