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
// TODO: need to create thread to handle fetching weather data
    private static final String ARG_COUNT = "param1";
    private Integer count;

    public ShowWeatherFragment() {
        // Required empty public constructor
    }

    public static ShowWeatherFragment newInstance(Integer count) {
        ShowWeatherFragment fragment = new ShowWeatherFragment();
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
        String currentWeather = getString(R.string.current_weather);
        String hrByHr = getString(R.string.hr_by_hr_forecast);
        String threeDay = getString(R.string.future_forecast);

        final String[] tabText = {currentWeather, hrByHr, threeDay};

        super.onViewCreated(view, savedInstanceState);

       // FetchWeather.getForecast("69", "69", R.);

        TextView textViewCounter = view.findViewById(R.id.weather_view);
        textViewCounter.setText(tabText[count]);
    }
}
