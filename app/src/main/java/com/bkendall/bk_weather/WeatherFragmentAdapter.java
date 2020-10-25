package com.bkendall.bk_weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherFragmentAdapter extends FragmentStateAdapter {
    private String currentWeather;
    private String hrByHr;
    private String futureForecast;
    private String apiKey;

    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity, final Context mainActivity, double lat, double lon) throws InterruptedException {
        super(fragmentActivity);

        final String latitude = String.valueOf(lat);
        final String longitude = String.valueOf(lon);

        apiKey = mainActivity.getString(R.string.api_key);

        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                try {

                    final JSONObject json = FetchWeather.getForecast(latitude, longitude, apiKey);

                    currentWeather = StringBuilder.currentWeatherString(json.getJSONObject(mainActivity.getString(R.string.weather_now)));
                    hrByHr = StringBuilder.hourByHourString(json.getJSONArray(mainActivity.getString(R.string.hourly)));
                    futureForecast = StringBuilder.futureForecastString(json.getJSONArray(mainActivity.getString(R.string.daily_weather)));
                } catch (JSONException e) {
                    currentWeather = mainActivity.getString(R.string.unexpected_error);
                    hrByHr = mainActivity.getString(R.string.unexpected_error);
                    futureForecast = mainActivity.getString(R.string.unexpected_error);
                } catch (IOException e) {
                    currentWeather = mainActivity.getString(R.string.unexpected_error);
                    hrByHr = mainActivity.getString(R.string.unexpected_error);
                    futureForecast = mainActivity.getString(R.string.unexpected_error);
                }
            }
        });
        t.start();
        t.join();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ShowWeatherFragment.newInstance(position, currentWeather, hrByHr, futureForecast);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
