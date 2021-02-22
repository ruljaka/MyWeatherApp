package com.ruslangrigoriev.weather.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruslangrigoriev.weather.data.entities.ForecastDataItem;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ForecastDataItemConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<ForecastDataItem> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ForecastDataItem>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<ForecastDataItem> dataItems) {
        return gson.toJson(dataItems);
    }
}
