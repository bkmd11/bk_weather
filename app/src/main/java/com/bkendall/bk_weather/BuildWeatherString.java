package com.bkendall.bk_weather;

import org.json.JSONException;
import org.json.JSONObject;

public class BuildWeatherString {
    static String currentWeather(JSONObject main) throws JSONException {
        String current_temp = "Current Temp: ";
        double temp = main.getDouble("temp");

        temp = (int) temp;

        current_temp = current_temp.concat(String.valueOf(temp));

        return current_temp;
    }
    static String currentLocation(JSONObject json) throws JSONException {
        String location = json.getString("name");

        return location;
    }
}
