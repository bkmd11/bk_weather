package com.bkendall.bk_weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class WeatherFragmentAdapter extends FragmentStateAdapter {
    String currentWeather;
    String hrByHr;
    String futureForecast;
    String apiKey;

    JSONObject jsonObject;

    // TODO: need to have this take all my strings
    //  Try a list/tuple type thing...
    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String api_key) {
        super(fragmentActivity);
        apiKey = api_key;


        new Thread(){
            public void run(){
                try {
                    final JSONObject json = FetchWeather.getForecast("0", "0", apiKey);
                    currentWeather = "FIX ME!";
                    hrByHr = "FIX ME TOO!!";
                    futureForecast = "DON'T FORGET ABOUT ME";
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
