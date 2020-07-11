package com.bkendall.bk_weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class FetchWeather {
    static JSONObject getCurrentTemp(String latitude, String longitude) throws IOException, JSONException {
        String OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=imperial";

        URL weatherURL = new URL(String.format(OPEN_WEATHER_MAP_API, latitude, longitude));
        HttpURLConnection weather = (HttpURLConnection) weatherURL.openConnection();
        weather.addRequestProperty("x-api-key", "291efc48fb9da8553ff4560029db809f");
        BufferedReader readWeather = new BufferedReader(new InputStreamReader(weather.getInputStream()));

        return new JSONObject(readWeather.readLine());
    }
}

