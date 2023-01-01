package com.bkendall.bk_weather;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bkendall.bk_weather.ui.main.AlertFragment;

public class AlertActivity extends AppCompatActivity {
    /*
    I get launched when a button is clicked to show what the weather alert is
     */
    private String alertString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        Intent intent = getIntent();
        alertString = intent.getStringExtra(getString(R.string.intent));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlertFragment.newInstance(alertString))
                    .commitNow();
        }
    }
}
