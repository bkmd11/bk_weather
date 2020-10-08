package com.bkendall.bk_weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowWeatherFragment extends Fragment {
    private Integer count;

    public ShowWeatherFragment() {
        // Required empty public constructor
    }

    public static ShowWeatherFragment newInstance(Integer count) {
        ShowWeatherFragment fragment = new ShowWeatherFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            count = 3;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_show_weather, container, false);
    }

    // TODO: Figure out how to set text for different tabs
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewCounter = view.findViewById(R.id.spam);
        textViewCounter.setText(R.string.current_weather);
    }
}
