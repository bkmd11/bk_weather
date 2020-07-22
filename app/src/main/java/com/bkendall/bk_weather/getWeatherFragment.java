package com.bkendall.bk_weather;

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
                                if (latitude.equals("-1.0") && longitude.equals("-1.0")) {
                                    currentWeather.setText("ERROR:\nNo GPS data");
                                }
                                else {
                                    renderWeather(json);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // I am the method for populating the textviews
    private void renderWeather(JSONObject weatherData) throws JSONException {

        String dailyWeather = getString(R.string.daily);
        String today = getString(R.string.today);

        //String current_location = BuildWeatherString.currentLocation(weatherData);
        String current_temp = BuildWeatherString.currentWeather(weatherData.getJSONObject(today));
        String today_forecast = BuildWeatherString.futureForecast(weatherData.getJSONArray(dailyWeather).getJSONObject(0));
        String tomorrow_forecast = BuildWeatherString.futureForecast(weatherData.getJSONArray(dailyWeather).getJSONObject(1));
        String two_days_out = BuildWeatherString.futureForecast(weatherData.getJSONArray(dailyWeather).getJSONObject(2));
        String three_days_out = BuildWeatherString.futureForecast(weatherData.getJSONArray(dailyWeather).getJSONObject(3));

        //location.setText(current_location);
        currentWeather.setText(current_temp);
        this_day.setText(today_forecast);
        tomorrow.setText(tomorrow_forecast);
        two_day.setText(two_days_out);
        three_day.setText(three_days_out);

    }


}

