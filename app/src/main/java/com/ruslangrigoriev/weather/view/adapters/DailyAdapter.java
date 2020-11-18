package com.ruslangrigoriev.weather.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslangrigoriev.weather.R;
import com.ruslangrigoriev.weather.data.entities.ForecastDataItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class DailyAdapter extends BaseAdapter {

    private final ArrayList<ForecastDataItem> dataItems;
    private final Context context;

    public DailyAdapter(Context context, ArrayList<ForecastDataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.layout_daily, parent, false);
        } else {
            grid = convertView;
        }

        TextView dayTV = grid.findViewById(R.id.dayTV);
        TextView dataTV = grid.findViewById(R.id.dataTV);
        TextView descTV = grid.findViewById(R.id.descTV);
        TextView dailyTempTV = grid.findViewById(R.id.dailyTempTV);
        ImageView weatherIV = grid.findViewById(R.id.weatherIV);

        ForecastDataItem dataItem = dataItems.get(position);

        DateTimeFormatter fIn = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
        LocalDate localDate = LocalDate.parse(dataItem.getValidDate(), fIn);
        //set date TextView
        DateTimeFormatter dateOut = DateTimeFormatter.ofPattern( "MMM dd" , Locale.getDefault() );
        String outputDate = localDate.format( dateOut );
        dataTV.setText(outputDate);

        //set day TextView
        if(position ==0){
            dayTV.setText(R.string.Today);
        } else {
            DateTimeFormatter dayOut = DateTimeFormatter.ofPattern("E", Locale.getDefault());
            String outputDay = localDate.format(dayOut);
            dayTV.setText(outputDay);
        }
        //set weather description
        descTV.setText(dataItem.getWeather().getDescription());
        descTV.setSelected(true);
        //set daily Temp
        dailyTempTV.setText(String.format(Locale.getDefault(), "%d°", Math.round(dataItem.getMaxTemp())));
        //set weather icon
        String iconID = dataItem.getWeather().getIcon();
        weatherIV.setImageResource(context.getResources().getIdentifier(iconID,"drawable",context.getPackageName()));

        return grid;
    }
}
