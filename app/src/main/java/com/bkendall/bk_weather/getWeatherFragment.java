package com.bkendall.bk_weather;

import android.annotation.SuppressLint;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class getWeatherFragment extends Fragment {
   // private TextView location;
    private TextView currentWeather;
    private TextView this_day;
    private TextView tomorrow;
    private TextView two_day;
    private TextView three_day;

    private Handler handler;

    private String latitude;
    private String longitude;
    private String secret_api_key;

    private Geocoder geocoder;


    getWeatherFragment(double lat, double lon, String api_key){
        latitude = String.valueOf(lat);
        longitude = String.valueOf(lon);
        secret_api_key = api_key;
        handler = new Handler();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_get_weather, container, false);
      //  location = rootView.findViewById(R.id.current_location);
        currentWeather = rootView.findViewById(R.id.current_temp);
        this_day = rootView.findViewById(R.id.this_day);
        tomorrow = rootView.findViewById(R.id.tomorrow);
        two_day = rootView.findViewById(R.id.two_day);
        three_day = rootView.findViewById(R.id.three_day);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getWeatherData();
    }

    private void getWeatherData(){
        new Thread(){
            public void run(){
                try {
                    final JSONObject json = FetchWeather.getForecast(latitude, longitude, secret_api_key);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                renderWeather(json);

                            } catch (JSONException e) {
                                currentWeather.setText(R.string.unexpected_error);
                            }
                        }
                    });
                } catch (IOException e) {
                    currentWeather.setText(R.string.unexpected_error);
                } catch (JSONException e) {
                    currentWeather.setText(R.string.unexpected_error);
                }
            }
        }.start();
    }


    // I am the method for populating the textviews
    private void renderWeather(JSONObject weatherData) throws JSONException {
        String dailyWeather = getString(R.string.daily_weather);
        String today = getString(R.string.todays_weather);

        //String current_location = BuildWeatherString.currentLocation(weatherData);
        String current_weather = currentWeatherString(weatherData.getJSONObject(today));
        String today_forecast = todayForecastString(weatherData.getJSONArray(dailyWeather).getJSONObject(0));
        String tomorrow_forecast = futureForecastString(weatherData.getJSONArray(dailyWeather).getJSONObject(1));
        String two_days_out = futureForecastString(weatherData.getJSONArray(dailyWeather).getJSONObject(2));
        String three_days_out = futureForecastString(weatherData.getJSONArray(dailyWeather).getJSONObject(3));

        //location.setText(current_location);
        currentWeather.setText(current_weather);
        this_day.setText(today_forecast);
        tomorrow.setText(tomorrow_forecast);
        two_day.setText(two_days_out);
        three_day.setText(three_days_out);
    }

    // I build the string to display the current weather
    private String currentWeatherString(JSONObject currentWeather) throws JSONException {
        JSONObject conditions = currentWeather.getJSONArray(getString(R.string.weather)).getJSONObject(0);

        String description = conditions.getString(getString(R.string.description));

        double temp = currentWeather.getDouble(getString(R.string.temperature));
        int temp_int = (int) temp;

        return String.format(getString(R.string.current_weather), description, temp_int);
    }

    private String todayForecastString(JSONObject json) throws JSONException {

        JSONObject temp = json.getJSONObject(getString(R.string.temperature));
        double max_temp = temp.getDouble(getString(R.string.highTemp));
        double min_temp = temp.getDouble(getString(R.string.lowTemp));

        int max_temp_int = (int) max_temp;
        int min_temp_int = (int) min_temp;

        JSONObject description = json.getJSONArray(getString(R.string.weather)).getJSONObject(0);
        String conditions = description.getString(getString(R.string.description));

        return String.format(getString(R.string.today_forecast), conditions, max_temp_int, min_temp_int);
    }

    private String futureForecastString(JSONObject json) throws JSONException {
        int unix_time = json.getInt(getString(R.string.dateTime));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dateFormat = new java.util.Date(unix_time * 1000L);
        String weekday = sdf.format(dateFormat);

        JSONObject temp = json.getJSONObject(getString(R.string.temperature));
        double max_temp = temp.getDouble(getString(R.string.highTemp));
        double min_temp = temp.getDouble(getString(R.string.lowTemp));

        int max_temp_int = (int) max_temp;
        int min_temp_int = (int) min_temp;

        JSONObject description = json.getJSONArray(getString(R.string.weather)).getJSONObject(0);
        String conditions = description.getString(getString(R.string.description));

        return String.format(getString(R.string.future_forecast), weekday, conditions, max_temp_int, min_temp_int);
    }
}