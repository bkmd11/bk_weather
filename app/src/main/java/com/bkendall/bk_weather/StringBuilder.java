package com.bkendall.bk_weather;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//TODO: put all strings at the top to maintain easier
//TODO: really need to clean up the code
//TODO: test test test to save time and headache

// I build strings for textViews
public class StringBuilder {

    @SuppressLint("DefaultLocale")
    public static String setCurrentWeatherString(JSONObject currentWeather, String weatherString) throws JSONException {
        // I build the string for the current weather tab
        String description = setConditions(currentWeather);

        int temp = setDoubleToInt(currentWeather.getDouble("temp"));

        return String.format(weatherString, description, temp);
    }

    @SuppressLint("DefaultLocale")
    public static String setHourByHourString(JSONArray hrByHr, String weatherString) throws JSONException {
        // I loop through a JSONArray to build an hour by hour string
        String hrByHrForecastString = "";

        int unix_time;
        int i;

        JSONObject hourlyForecast;

        for (i = 0; i < 12; i++) {
            hourlyForecast = hrByHr.getJSONObject(i);
            unix_time = hourlyForecast.getInt("dt");

            String hour = setDateString(unix_time, "EEE MMM d HH:mm:ss zzz yyyy");
            String hourString = hour.substring(11, 16);

            int temp_int = setDoubleToInt(hourlyForecast.getDouble("temp"));

            String conditions = setConditions(hourlyForecast);

            hrByHrForecastString = hrByHrForecastString.concat(String.format(weatherString, hourString, conditions, temp_int));
        }

        return hrByHrForecastString;
    }

    @SuppressLint("DefaultLocale")
    public static String setFutureForecastString(JSONArray futureForecast, String weatherString) throws JSONException {
        // I loop through a JSONArray to build a daily forecast string
        String futureForecastString = "";

        int unix_time;
        int i;

        JSONObject dailyForecast;

        for (i = 0; i < 5; i++) {
            dailyForecast = futureForecast.getJSONObject(i);
            unix_time = dailyForecast.getInt("dt");

            String weekday = setDateString(unix_time, "EEEE");

            JSONObject temp = dailyForecast.getJSONObject("temp");
            int max_temp = setDoubleToInt(temp.getDouble("max"));
            int min_temp = setDoubleToInt(temp.getDouble("min"));

            String conditions = setConditions(dailyForecast);

            futureForecastString = futureForecastString.concat(String.format(weatherString, weekday, conditions, max_temp, min_temp));
        }

        return futureForecastString;
    }

    private static String setConditions(JSONObject weather) throws JSONException {
        // I return a string of forecasted conditions
        JSONObject description = weather.getJSONArray("weather").getJSONObject(0);
        String conditions = description.getString("description");

        return setFirstLetterCap(conditions);
    }

    private static int setDoubleToInt(double temp){
        // I convert a double into an int
        return (int) temp;
    }

    private static String setDateString(int unix_time, String pattern){
        // I return a date to the specified format
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date dateFormat = new java.util.Date(unix_time * 1000L);

        return sdf.format(dateFormat);
    }

    private static String setFirstLetterCap(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
