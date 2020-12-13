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

    public String alert;

    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity, final Context mainActivity, double lat, double lon) throws InterruptedException {
        /*
        I am an adapter that controls the construction of fragments for the multi tabbed view.
        I also call the weather api and handle that data in the background, but I cannot actually
        change any of the views
         */
        super(fragmentActivity);

        final String latitude = String.valueOf(lat);
        final String longitude = String.valueOf(lon);

        apiKey = mainActivity.getString(R.string.api_key);

        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                try {

                    final JSONObject json = FetchWeather.getForecast(latitude, longitude, apiKey);
                    currentWeather = StringHandler.setCurrentWeatherString(json.getJSONObject(mainActivity.getString(R.string.weather_now)),
                            mainActivity.getString(R.string.current_weather));
                    hrByHr = StringHandler.setHourByHourString(json.getJSONArray(mainActivity.getString(R.string.hourly)),
                            mainActivity.getString(R.string.hr_by_hr_forecast));
                    futureForecast = StringHandler.setFutureForecastString(json.getJSONArray(mainActivity.getString(R.string.daily_weather)),
                           mainActivity.getString(R.string.future_forecast));

                    try {
                        alert = StringHandler.setAlertString(json.getJSONArray(mainActivity.getString(R.string.alert)));

                    } catch (JSONException e) {
                        alert = "SPAM AND EGGS";
                    }

                } catch (JSONException e) {
                    currentWeather = mainActivity.getString(R.string.unexpected_error);
                    hrByHr = mainActivity.getString(R.string.unexpected_error);
                    futureForecast = mainActivity.getString(R.string.unexpected_error);
                    e.printStackTrace();
                } catch (IOException e) {
                    currentWeather = mainActivity.getString(R.string.unexpected_error);
                    hrByHr = mainActivity.getString(R.string.unexpected_error);
                    futureForecast = mainActivity.getString(R.string.unexpected_error);
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        final String[] weatherStrings = {currentWeather, hrByHr, futureForecast};
        return ShowWeatherFragment.newInstance(position, weatherStrings[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
