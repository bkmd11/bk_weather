package com.bkendall.bk_weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class WeatherFragmentAdapter extends FragmentStateAdapter {
    private String currentWeather;
    private String hrByHr;
    private String futureForecast;
    private String apiKey;

    public String alert;
    public String alertType;

    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity, final Context mainActivity, double lat, double lon) throws InterruptedException {
        /*
        I am an adapter that controls the construction of fragments for the multi tabbed view.
        I also call the weather api and handle that data in the background, but I cannot actually
        change any of the views
         */
        super(fragmentActivity);

        final String FILE_NAME = mainActivity.getString(R.string.fileName);
        final double LATITUDE = lat;
        final double LONGITUDE = lon;

        final FileHandler fileHandler = new FileHandler();

        apiKey = mainActivity.getString(R.string.api_key);

        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    JSONObject json;

                    String filePath = mainActivity.getFilesDir().getAbsolutePath() + "/" + FILE_NAME;

                    // Checks if the file exists and is less than 15 minutes old. If both are correct, the code runs
                    if (LATITUDE==0 && LONGITUDE==0) {
                        currentWeather = mainActivity.getString(R.string.gps_error);
                        hrByHr = mainActivity.getString(R.string.gps_error);
                        futureForecast = mainActivity.getString(R.string.gps_error);
                        alert = "";
                    }
                    else {
                        if (fileHandler.checkIfFileExists(filePath)
                                && fileHandler.fileModifyDate(filePath)) {
                            json = new JSONObject(fileHandler.readFile(new File(filePath)));
                        } else {
                            json = FetchWeather.getForecast(LATITUDE, LONGITUDE, apiKey);
                            fileHandler.createFile(filePath, json.toString());
                        }
                        StringHandler stringHandler = new StringHandler(mainActivity, json);
                        currentWeather = stringHandler.currentWeather;
                        hrByHr = stringHandler.hrByHr;
                        futureForecast = stringHandler.futureForecast;
                        alertType = stringHandler.alertType;
                        alert = stringHandler.alertString;
                    }
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
        final String[] weatherStrings = {currentWeather, hrByHr, futureForecast};
        return ShowWeatherFragment.newInstance(position, weatherStrings[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
