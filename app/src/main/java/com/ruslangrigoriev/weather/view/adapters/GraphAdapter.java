package com.ruslangrigoriev.weather.view.adapters;

import com.robinhood.spark.SparkAdapter;

public class GraphAdapter extends SparkAdapter {
    private final float[] data;

    public GraphAdapter(float[] yData) {
        this.data = yData;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int index) {
        return data[index];
    }

    @Override
    public float getY(int index) {
        return data[index];
    }
}
