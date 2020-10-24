package com.bkendall.bk_weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowWeatherFragment extends Fragment {
    private static final String ARG_COUNT = "param1";
    private Integer count;

    private String currentWeather;
    private String hrByHr;
    private String threeDay;

    public ShowWeatherFragment(String current_weather, String hr_by_hr, String future_forecast) {
        currentWeather = current_weather;
        hrByHr = hr_by_hr;
        threeDay = future_forecast;
    }

    public static ShowWeatherFragment newInstance(Integer count, String current_weather, String hr_by_hr, String future_forecast) {
        ShowWeatherFragment fragment = new ShowWeatherFragment(current_weather, hr_by_hr, future_forecast);
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, count);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            count = getArguments().getInt(ARG_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_show_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String[] tabText = {currentWeather, hrByHr, threeDay};

        super.onViewCreated(view, savedInstanceState);
        // TODO: could try putting stringbuilder stuff here with case statements
        TextView textViewCounter = view.findViewById(R.id.weather_view);
        textViewCounter.setText(tabText[count]);
    }
}
