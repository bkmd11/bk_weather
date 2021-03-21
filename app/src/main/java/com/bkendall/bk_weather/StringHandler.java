package com.bkendall.bk_weather;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// I build strings for textViews
public class StringHandler {

    @SuppressLint("DefaultLocale")
    public static String setCurrentWeatherString(JSONObject currentWeather, String weatherString) throws JSONException {
        // I build the string for the current weather tab
        String description = setConditions(currentWeather);

        String sunrise_time = setDateString(currentWeather.getInt("sunrise"), "EEE MMM dd HH:mm:ss zzz yyyy");
        String sunrise = getTimeRegex(sunrise_time);

        String sunset_time = setDateString(currentWeather.getInt("sunset"), "EEE MMM dd HH:mm:ss zzz yyyy");
        String sunset = getTimeRegex(sunset_time);

        int temp = setDoubleToInt(currentWeather.getDouble("temp"));

        return String.format(weatherString, description, temp, sunrise, sunset);
    }

    @SuppressLint("DefaultLocale")
    public static String setHourByHourString(JSONArray hrByHr, String weatherString) throws JSONException {
        // I loop through a JSONArray to build an hour by hour string
        String hrByHrForecastString = "";

        int unix_time;
        int i;

        JSONObject hourlyForecast;

        for (i = 0; i < 24; i++) {
            hourlyForecast = hrByHr.getJSONObject(i);
            unix_time = hourlyForecast.getInt("dt");

            String hour = setDateString(unix_time, "EEE MMM dd HH:mm:ss zzz yyyy");

            String hourString = getTimeRegex(hour);
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

        for (i = 0; i < 7; i++) {
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

    public static String setAlertString(JSONArray alertForecast) throws JSONException {
        // I return a string of the alert message
        JSONObject alert = alertForecast.getJSONObject(0);

        return alert.getString("description").concat("\n");
    }

    public static String setEventString(JSONArray alertForecast) throws JSONException {
        JSONObject alert = alertForecast.getJSONObject(0);

        return alert.getString("event");
    }

    public static String setConditions(JSONObject weather) throws JSONException {
        // I return a string of forecasted conditions
        JSONObject description = weather.getJSONArray("weather").getJSONObject(0);
        String conditions = description.getString("description");

        return setFirstLetterCap(conditions);
    }

    public static int setDoubleToInt(double temp){
        // I convert a double into an int
        return (int) temp;
    }

    public static String setDateString(int unix_time, String pattern){
        // I return a date to the specified format
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date dateFormat = new java.util.Date(unix_time * 1000L);

        return sdf.format(dateFormat);
    }

    public static String setFirstLetterCap(String string){
        // I capitalize the first letter of a string
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String getTimeRegex(String unixTime){
        // I find the time regex from a unix timestamp string
        Pattern timePattern = Pattern.compile("\\d\\d:\\d\\d");
        Matcher timeMatch = timePattern.matcher(unixTime);

        if (timeMatch.find()) {
            return convertStandardTime(timeMatch.group(0));
        }
        else {
            return "XX:XX";
        }
    }

    public static String convertStandardTime(String time){
        // I take a string of military time and convert it to standard time
        String am_pm = "AM";
        int hours = Integer.parseInt(time.substring(0, 2));

        if (hours > 11){
            am_pm = "PM";
        }

        if (hours > 12){
            hours = hours - 12;
        }
        else if (hours == 0){
            hours = 12;
        }

        time = hours + time.substring(2, 5) + " " + am_pm;

        return time;
    }
}
