package com.ruslangrigoriev.weather.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Forecast {

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("data")
	private List<ForecastDataItem> data;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("lon")
	private String lon;

	@SerializedName("state_code")
	private String stateCode;

	@SerializedName("lat")
	private String lat;

	public String getCountryCode(){
		return countryCode;
	}

	public String getCityName(){
		return cityName;
	}

	public List<ForecastDataItem> getData(){
		return data;
	}

	public String getTimezone(){
		return timezone;
	}

	public String getLon(){
		return lon;
	}

	public String getStateCode(){
		return stateCode;
	}

	public String getLat(){
		return lat;
	}
}