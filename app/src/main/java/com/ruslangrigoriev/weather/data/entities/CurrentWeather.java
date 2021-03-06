package com.ruslangrigoriev.weather.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ruslangrigoriev.weather.data.DataItemConverter;

import java.util.List;

@Entity(tableName = "current_weather")
public class CurrentWeather {

    @PrimaryKey
    private int id = 1;

    @SerializedName("data")
    @TypeConverters(DataItemConverter.class)
    private List<DataItem> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }
}