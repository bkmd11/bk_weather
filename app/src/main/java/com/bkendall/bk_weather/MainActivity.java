package com.bkendall.bk_weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;


import android.Manifest;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//  TODO: need to get location again.
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Tab titles
        String current_weather = getString(R.string.current);
        String hr_by_hr = getString(R.string.hr_by_hr);
        String future_forecast = getString(R.string.future_cast);
        final String[] tabTitles = {current_weather, hr_by_hr, future_forecast};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        // TODO: I can put my strings in here
        viewPager2.setAdapter(createMyAdapter("python is better"));

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabTitles[position]);
                    }
                }).attach();
    }
    private WeatherFragmentAdapter createMyAdapter(String shade) {
        WeatherFragmentAdapter adapter = new WeatherFragmentAdapter(this, "FIX ME", shade);
        return adapter;
    }
}




