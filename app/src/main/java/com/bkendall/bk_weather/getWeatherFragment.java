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
    TextView currentWeather;
    Handler handler;


    public getWeatherFragment(){
        handler = new Handler();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_get_weather, container, false);
        currentWeather = (TextView)rootView.findViewById(R.id.current_temp);

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
                    final JSONObject json = FetchWeather.getCurrentTemp();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                renderWeather(json);
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

    private void renderWeather(JSONObject weatherData) throws JSONException {
        JSONObject main = new JSONObject(weatherData.getJSONObject("main").toString());
        currentWeather.setText(main.getString("temp"));
    }


}

