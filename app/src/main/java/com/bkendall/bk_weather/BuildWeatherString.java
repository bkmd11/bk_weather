package com.bkendall.bk_weather;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildWeatherString {
    // I return the current temp from a json object
    static String currentWeather(JSONObject currentWeather) throws JSONException {
        String current_temp = "Current Weather\n";
        JSONObject conditions = currentWeather.getJSONArray("weather").getJSONObject(0);

        String description = conditions.getString("description");

        double temp = currentWeather.getDouble("temp");
        temp = (int) temp;

        current_temp = current_temp.concat(description + "\n");
        current_temp = current_temp.concat(String.valueOf(temp));

        return current_temp;
    }

    // I get the current location
    static String currentLocation(JSONObject json) throws JSONException {
        //TODO get location from gps
        String location = json.getString("name");

        return location;
    }

    // I build the extended forecast
    static String futureForecast(JSONObject json) throws JSONException {
        String forecast = "";
        int unix_time = json.getInt("dt");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dateFormat = new java.util.Date(unix_time * 1000L);
        String weekday = sdf.format(dateFormat);

        JSONObject temp = json.getJSONObject("temp");
        double max_temp = temp.getDouble("max");

        max_temp = (int) max_temp;

        JSONObject description = json.getJSONArray("weather").getJSONObject(0);
        String coniditions = description.getString("description");

        forecast = forecast.concat(weekday + "\n");
        forecast = forecast.concat(coniditions + "\n");
        forecast = forecast.concat("Temp: " + max_temp);

        return forecast;
    }
}
