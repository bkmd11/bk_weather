package com.bkendall.bk_weather;

import android.annotation.SuppressLint;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: set view builder things so they happen in order, not always the case

// I build strings for textViews
public class StringHandler {
    String currentWeather;
    String hrByHr;
    String futureForecast;
    String alertString;
    String alertType;
    Context context;

    public StringHandler(Context mainContext, JSONObject jsonObject) throws JSONException {
        context = mainContext;
        currentWeather = setCurrentWeatherString(jsonObject.getJSONObject(context.getString(R.string.weather_now)),
                context.getString(R.string.current_weather));
        hrByHr = setHourByHourString(jsonObject.getJSONArray(context.getString(R.string.hourly)),
                context.getString(R.string.hr_by_hr_forecast));
        futureForecast = setFutureForecastString(jsonObject.getJSONArray(context.getString(R.string.daily_weather)),
                context.getString(R.string.future_forecast));
        try{
            alertString = setAlertString(jsonObject.getJSONArray(context.getString(R.string.alert)));
            alertType = setEventString(jsonObject.getJSONArray(context.getString(R.string.alert)));
        } catch (JSONException e){
            alertString = "";
            alertType = "";
        }
    }

    @SuppressLint("DefaultLocale")
    private String setCurrentWeatherString(JSONObject currentWeather, String weatherString) throws JSONException {
        // I build the string for the current weather tab
        String description = setConditions(currentWeather);

        String sunrise_time = setDateString(currentWeather.getInt(context.getString(R.string.rise)), context.getString(R.string.timeFormat));
        String sunrise = getTimeRegex(sunrise_time);

        String sunset_time = setDateString(currentWeather.getInt(context.getString(R.string.set)), context.getString(R.string.timeFormat));
        String sunset = getTimeRegex(sunset_time);

        int temp = setDoubleToInt(currentWeather.getDouble(context.getString(R.string.temperature)));
        int wind_speed = setDoubleToInt(currentWeather.getDouble(context.getString(R.string.wind_speed)));

        return String.format(weatherString, description, temp, sunrise, sunset, wind_speed);
    }

    @SuppressLint("DefaultLocale")
    private String setHourByHourString(JSONArray hrByHr, String weatherString) throws JSONException {
        // I loop through a JSONArray to build an hour by hour string
        String hrByHrForecastString = "";

        int unix_time;
        int i;

        JSONObject hourlyForecast;

        for (i = 0; i < 24; i++) {
            hourlyForecast = hrByHr.getJSONObject(i);
            unix_time = hourlyForecast.getInt(context.getString(R.string.dateTime));

            String hour = setDateString(unix_time, context.getString(R.string.timeFormat));

            String hourString = getTimeRegex(hour);
            int temp_int = setDoubleToInt(hourlyForecast.getDouble(context.getString(R.string.temperature)));

            String conditions = setConditions(hourlyForecast);

            int wind_speed = setDoubleToInt(hourlyForecast.getDouble(context.getString(R.string.wind_speed)));

            hrByHrForecastString = hrByHrForecastString.concat(String.format(weatherString, hourString, conditions, temp_int, wind_speed));
        }

        return hrByHrForecastString;
    }

    @SuppressLint("DefaultLocale")
    private String setFutureForecastString(JSONArray futureForecast, String weatherString) throws JSONException {
        // I loop through a JSONArray to build a daily forecast string
        String futureForecastString = "";

        int unix_time;
        int i;

        JSONObject dailyForecast;

        for (i = 0; i < 7; i++) {
            dailyForecast = futureForecast.getJSONObject(i);
            unix_time = dailyForecast.getInt(context.getString(R.string.dateTime));

            String weekday = setDateString(unix_time, context.getString(R.string.dayFormat));

            JSONObject temp = dailyForecast.getJSONObject(context.getString(R.string.temperature));
            int max_temp = setDoubleToInt(temp.getDouble(context.getString(R.string.highTemp)));
            int min_temp = setDoubleToInt(temp.getDouble(context.getString(R.string.lowTemp)));

            String conditions = setConditions(dailyForecast);

            int wind_speed = setDoubleToInt(dailyForecast.getDouble(context.getString(R.string.wind_speed)));

            futureForecastString = futureForecastString.concat(String.format(weatherString, weekday, conditions, max_temp, min_temp, wind_speed));
        }

        return futureForecastString;
    }

    private String setAlertString(JSONArray alertForecast) throws JSONException{
        // I return a string of the alert message
        JSONObject alert = alertForecast.getJSONObject(0);

        return alert.getString(context.getString(R.string.description)).concat("\n");
    }

    private String setEventString(JSONArray alertForecast) throws JSONException {
        JSONObject alert = alertForecast.getJSONObject(0);

        return alert.getString(context.getString(R.string.event));
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
