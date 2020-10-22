package com.bkendall.bk_weather;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//TODO: put all strings at the top to maintain easier

// I build strings for textViews
public class StringBuilder {

    @SuppressLint("DefaultLocale")
    public static String currentWeatherString(JSONObject currentWeather) throws JSONException {
        // I build the string for the current weather tab
        JSONObject conditions = currentWeather.getJSONArray("weather").getJSONObject(0);

        String description = conditions.getString("description");

        double temp = currentWeather.getDouble("temp");
        int temp_int = (int) temp;

        return String.format("Current Weather\n\t\t\t\t%s\n\t\t\t\t%d", description, temp_int);
    }

    public static String hourByHourString(JSONArray hrByHr){
        return "I DONT WORK!!!";
    }

    public static String futureForecastString(JSONArray futureForecast){
        String futureForecastString = "";
        String forecastString = "%s\n\t\t\t\t%s\n\t\t\t\tHigh: %d\n\t\t\t\tLow: %d\n\n";
        //TODO: I need to get the JSONObject out of the JSONArray
        int unix_time = futureForecast.getInt("dt");

        int i;

        for (i = 0; i==2; i++) {

        }
        return futureForecastString;
    }
}
