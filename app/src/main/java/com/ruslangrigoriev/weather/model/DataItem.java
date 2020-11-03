package com.ruslangrigoriev.weather.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("sunrise")
	private String sunrise;

	@SerializedName("pod")
	private String pod;

	@SerializedName("pres")
	private double pres;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("ob_time")
	private String obTime;

	@SerializedName("wind_cdir")
	private String windCdir;

	@SerializedName("lon")
	private double lon;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("wind_spd")
	private double windSpd;

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("h_angle")
	private int hAngle;

	@SerializedName("datetime")
	private String datetime;

	@SerializedName("precip")
	private double precip;

	@SerializedName("weather")
	private Weather weather;

	@SerializedName("station")
	private String station;

	@SerializedName("elev_angle")
	private double elevAngle;

	@SerializedName("dni")
	private double dni;

	@SerializedName("lat")
	private double lat;

	@SerializedName("vis")
	private double vis;

	@SerializedName("uv")
	private double uv;

	@SerializedName("temp")
	private double temp;

	@SerializedName("dhi")
	private double dhi;

	@SerializedName("ghi")
	private double ghi;

	@SerializedName("app_temp")
	private double appTemp;

	@SerializedName("dewpt")
	private double dewpt;

	@SerializedName("wind_dir")
	private int windDir;

	@SerializedName("solar_rad")
	private double solarRad;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("rh")
	private double rh;

	@SerializedName("slp")
	private double slp;

	@SerializedName("snow")
	private int snow;

	@SerializedName("sunset")
	private String sunset;

	@SerializedName("aqi")
	private int aqi;

	@SerializedName("state_code")
	private String stateCode;

	@SerializedName("wind_cdir_full")
	private String windCdirFull;

	@SerializedName("ts")
	private int ts;

	public String getSunrise(){
		return sunrise;
	}

	public String getPod(){
		return pod;
	}

	public double getPres(){
		return pres;
	}

	public String getTimezone(){
		return timezone;
	}

	public String getObTime(){
		return obTime;
	}

	public String getWindCdir(){
		return windCdir;
	}

	public double getLon(){
		return lon;
	}

	public int getClouds(){
		return clouds;
	}

	public double getWindSpd(){
		return windSpd;
	}

	public String getCityName(){
		return cityName;
	}

	public int getHAngle(){
		return hAngle;
	}

	public String getDatetime(){
		return datetime;
	}

	public double getPrecip(){
		return precip;
	}

	public Weather getWeather(){
		return weather;
	}

	public String getStation(){
		return station;
	}

	public double getElevAngle(){
		return elevAngle;
	}

	public double getDni(){
		return dni;
	}

	public double getLat(){
		return lat;
	}

	public double getVis(){
		return vis;
	}

	public double getUv(){
		return uv;
	}

	public double getTemp(){
		return temp;
	}

	public double getDhi(){
		return dhi;
	}

	public double getGhi(){
		return ghi;
	}

	public double getAppTemp(){
		return appTemp;
	}

	public double getDewpt(){
		return dewpt;
	}

	public int getWindDir(){
		return windDir;
	}

	public double getSolarRad(){
		return solarRad;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public double getRh(){
		return rh;
	}

	public double getSlp(){
		return slp;
	}

	public int getSnow(){
		return snow;
	}

	public String getSunset(){
		return sunset;
	}

	public int getAqi(){
		return aqi;
	}

	public String getStateCode(){
		return stateCode;
	}

	public String getWindCdirFull(){
		return windCdirFull;
	}

	public int getTs(){
		return ts;
	}
}