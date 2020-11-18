package com.ruslangrigoriev.weather.data.entities;

import com.google.gson.annotations.SerializedName;

public class ForecastDataItem {

	@SerializedName("pres")
	private double pres;

	@SerializedName("moon_phase")
	private double moonPhase;

	@SerializedName("wind_cdir")
	private String windCdir;

	@SerializedName("moonrise_ts")
	private int moonriseTs;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("low_temp")
	private double lowTemp;

	@SerializedName("wind_spd")
	private double windSpd;

	@SerializedName("ozone")
	private double ozone;

	@SerializedName("pop")
	private int pop;

	@SerializedName("valid_date")
	private String validDate;

	@SerializedName("datetime")
	private String datetime;

	@SerializedName("precip")
	private double precip;

	@SerializedName("sunrise_ts")
	private int sunriseTs;

	@SerializedName("min_temp")
	private double minTemp;

	@SerializedName("weather")
	private Weather weather;

	@SerializedName("app_max_temp")
	private double appMaxTemp;

	@SerializedName("max_temp")
	private double maxTemp;

	@SerializedName("snow_depth")
	private double snowDepth;

	@SerializedName("sunset_ts")
	private int sunsetTs;

	@SerializedName("max_dhi")
	private Object maxDhi;

	@SerializedName("clouds_mid")
	private int cloudsMid;

	@SerializedName("vis")
	private double vis;

	@SerializedName("uv")
	private double uv;

	@SerializedName("high_temp")
	private double highTemp;

	@SerializedName("temp")
	private double temp;

	@SerializedName("clouds_hi")
	private int cloudsHi;

	@SerializedName("app_min_temp")
	private double appMinTemp;

	@SerializedName("moon_phase_lunation")
	private double moonPhaseLunation;

	@SerializedName("dewpt")
	private double dewpt;

	@SerializedName("wind_dir")
	private int windDir;

	@SerializedName("wind_gust_spd")
	private double windGustSpd;

	@SerializedName("clouds_low")
	private int cloudsLow;

	@SerializedName("rh")
	private int rh;

	@SerializedName("slp")
	private double slp;

	@SerializedName("snow")
	private double snow;

	@SerializedName("wind_cdir_full")
	private String windCdirFull;

	@SerializedName("moonset_ts")
	private int moonsetTs;

	@SerializedName("ts")
	private int ts;

	public double getPres(){
		return pres;
	}

	public double getMoonPhase(){
		return moonPhase;
	}

	public String getWindCdir(){
		return windCdir;
	}

	public int getMoonriseTs(){
		return moonriseTs;
	}

	public int getClouds(){
		return clouds;
	}

	public double getLowTemp(){
		return lowTemp;
	}

	public double getWindSpd(){
		return windSpd;
	}

	public double getOzone(){
		return ozone;
	}

	public int getPop(){
		return pop;
	}

	public String getValidDate(){
		return validDate;
	}

	public String getDatetime(){
		return datetime;
	}

	public double getPrecip(){
		return precip;
	}

	public int getSunriseTs(){
		return sunriseTs;
	}

	public double getMinTemp(){
		return minTemp;
	}

	public Weather getWeather(){
		return weather;
	}

	public double getAppMaxTemp(){
		return appMaxTemp;
	}

	public double getMaxTemp(){
		return maxTemp;
	}

	public double getSnowDepth(){
		return snowDepth;
	}

	public int getSunsetTs(){
		return sunsetTs;
	}

	public Object getMaxDhi(){
		return maxDhi;
	}

	public int getCloudsMid(){
		return cloudsMid;
	}

	public double getVis(){
		return vis;
	}

	public double getUv(){
		return uv;
	}

	public double getHighTemp(){
		return highTemp;
	}

	public double getTemp(){
		return temp;
	}

	public int getCloudsHi(){
		return cloudsHi;
	}

	public double getAppMinTemp(){
		return appMinTemp;
	}

	public double getMoonPhaseLunation(){
		return moonPhaseLunation;
	}

	public double getDewpt(){
		return dewpt;
	}

	public int getWindDir(){
		return windDir;
	}

	public double getWindGustSpd(){
		return windGustSpd;
	}

	public int getCloudsLow(){
		return cloudsLow;
	}

	public int getRh(){
		return rh;
	}

	public double getSlp(){
		return slp;
	}

	public double getSnow(){
		return snow;
	}

	public String getWindCdirFull(){
		return windCdirFull;
	}

	public int getMoonsetTs(){
		return moonsetTs;
	}

	public int getTs(){
		return ts;
	}
}