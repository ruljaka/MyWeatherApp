package com.ruslangrigoriev.weather.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruslangrigoriev.weather.data.entities.DataItem;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataItemConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<DataItem> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<DataItem>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<DataItem> dataItems) {
        return gson.toJson(dataItems);
    }
}
