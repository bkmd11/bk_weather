package com.bkendall.bk_weather.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bkendall.bk_weather.R;

public class AlertFragment extends Fragment {
    /*
    I am a fragment for the AlertActivity. My job is to populate a text view with the alert message
     */
    private String alertString;

    public AlertFragment(String alert_string){
        alertString = alert_string;
    }

    public static AlertFragment newInstance(String alert_string) {
        return new AlertFragment(alert_string);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alert_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewCounter = view.findViewById(R.id.alert_view);
        textViewCounter.setText(alertString);
    }
}