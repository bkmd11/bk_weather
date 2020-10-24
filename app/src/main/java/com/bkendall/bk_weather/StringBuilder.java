package com.bkendall.bk_weather;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @SuppressLint("DefaultLocale")
    public static String hourByHourString(JSONArray hrByHr) throws JSONException {
        // I loop through a JSONArray to build an hour by hour string
        String hrByHrForecastString = "";
        String hrString = "%s\n\t\t\t\t%s\n\t\t\t\tTemp: %d\n\n";
        int unix_time;
        int i;

        JSONObject hourlyForecast;

        for (i = 0; i < 12; i++) {
            hourlyForecast = hrByHr.getJSONObject(i);
            unix_time = hourlyForecast.getInt("dt");

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            Date dateFormat = new java.util.Date(unix_time * 1000L);
            String hour = sdf.format(dateFormat);
            String hourString = hour.substring(11, 16);

            double temp = hourlyForecast.getDouble("temp");

            JSONObject description = hourlyForecast.getJSONArray("weather").getJSONObject(0);
            String conditions = description.getString("description");

            hrByHrForecastString = hrByHrForecastString.concat(String.format(hrString, hourString, conditions, (int) temp));
        }

        return hrByHrForecastString;
    }

    @SuppressLint("DefaultLocale")
    public static String futureForecastString(JSONArray futureForecast) throws JSONException {
        // I loop through a JSONArray to build a daily forecast string
        String futureForecastString = "";
        String forecastString = "%s\n\t\t\t\t%s\n\t\t\t\tHigh: %d\n\t\t\t\tLow: %d\n\n";
        int unix_time;
        int i;

        JSONObject dailyForecast;

        for (i = 0; i < 4; i++) {
            dailyForecast = futureForecast.getJSONObject(i);
            unix_time = dailyForecast.getInt("dt");

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date dateFormat = new java.util.Date(unix_time * 1000L);
            String weekday = sdf.format(dateFormat);

            JSONObject temp = dailyForecast.getJSONObject("temp");
            double max_temp = temp.getDouble("max");
            double min_temp = temp.getDouble("min");

            int max_temp_int = (int) max_temp;
            int min_temp_int = (int) min_temp;

            JSONObject description = dailyForecast.getJSONArray("weather").getJSONObject(0);
            String conditions = description.getString("description");

            futureForecastString = futureForecastString.concat(String.format(forecastString, weekday, conditions, max_temp_int, min_temp_int));
        }

        return futureForecastString;
    }
}
