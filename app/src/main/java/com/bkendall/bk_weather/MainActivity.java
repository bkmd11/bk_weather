package com.bkendall.bk_weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;


import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// TODO: this thing can't really handle errors
public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    double lat;
    double lon;

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

        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // TODO: look to update to get current location

            lat = location.getLatitude();
            lon = location.getLongitude();


            if(savedInstanceState == null) {
                tabLayout = findViewById(R.id.tabLayout);
                viewPager2 = findViewById(R.id.viewPager2);

                try {
                    viewPager2.setAdapter(createMyAdapter());
                } catch (InterruptedException e) {
                    TextView errorText = findViewById(R.id.error_message);
                    errorText.setText(R.string.unexpected_error);
                }

                new TabLayoutMediator(tabLayout, viewPager2,
                        new TabLayoutMediator.TabConfigurationStrategy() {
                            @Override
                            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                tab.setText(tabTitles[position]);
                            }
                        }).attach();
            }
        } catch (NullPointerException e) {
            TextView error = findViewById(R.id.error_gps);
            error.setText(R.string.gps_error);
        }
    }

    private WeatherFragmentAdapter createMyAdapter() throws InterruptedException {
        return new WeatherFragmentAdapter(this, this, lat, lon);
    }
}




