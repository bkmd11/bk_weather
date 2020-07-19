package com.bkendall.bk_weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class FetchWeather {

    // I make a call to the API and return a 5 day forecast
    static JSONObject getForecast(String latitude, String longitude, String api_key) throws IOException, JSONException {

        String OPEN_WEATHER_MAP_API = //String.valueOf(R.string.url);
               "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=minutely,hourly&units=imperial";
       // System.out.println(OPEN_WEATHER_MAP_API);

        URL weatherURL = new URL(String.format(OPEN_WEATHER_MAP_API, latitude, longitude));
        HttpURLConnection weather = (HttpURLConnection) weatherURL.openConnection();
        weather.addRequestProperty("x-api-key", api_key);
        BufferedReader readWeather = new BufferedReader(new InputStreamReader(weather.getInputStream()));

        return new JSONObject(readWeather.readLine());
    }
}

