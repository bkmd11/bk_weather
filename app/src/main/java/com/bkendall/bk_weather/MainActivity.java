package com.bkendall.bk_weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

// TODO: this thing can't really handle errors
public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    TextView alertView;
    Button alertButton;

    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
             ActivityCompat.requestPermissions(this,
                     new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
         else {
             setCoordinates();
             setForecastViews();
         }
    }

    private WeatherFragmentAdapter createMyAdapter() throws InterruptedException {
        final WeatherFragmentAdapter weatherFragmentAdapter = new WeatherFragmentAdapter(this, this, lat, lon);

        if (weatherFragmentAdapter.alert.equals("")) {
            alertButton = findViewById(R.id.alert);
            alertButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    alertView = findViewById(R.id.alertView);
                    alertView.setVisibility(View.VISIBLE);
                    alertView.setText("SPAM");
                }
            });

            alertButton.setVisibility(View.VISIBLE);
        }
        return weatherFragmentAdapter;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions,
                                           @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        setCoordinates();
        setForecastViews();
    }

    private void setForecastViews(){

        // Tab titles
        String current_weather = getString(R.string.current);
        String hr_by_hr = getString(R.string.hr_by_hr);
        String future_forecast = getString(R.string.future_cast);
        final String[] tabTitles = {current_weather, hr_by_hr, future_forecast};

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        try {
            viewPager2.setAdapter(createMyAdapter());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabTitles[position]);
                    }
                }).attach();

    }

    private void setCoordinates(){
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert lm != null;
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // TODO: look to update to get current location

            assert location != null;
            lat = location.getLatitude();
            lon = location.getLongitude();
        } catch (NullPointerException e) {
            e.printStackTrace();
            lat = 0;
            lon = 0;
        }
    }


}




