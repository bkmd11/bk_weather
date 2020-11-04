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

    private String weatherString;

    public ShowWeatherFragment(String weather_string) {
        weatherString = weather_string;

    }

    public static ShowWeatherFragment newInstance(Integer count, String weather_strings) {
        ShowWeatherFragment fragment = new ShowWeatherFragment(weather_strings);
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

        super.onViewCreated(view, savedInstanceState);
        TextView textViewCounter = view.findViewById(R.id.weather_view);
        textViewCounter.setText(weatherString);
    }
}
