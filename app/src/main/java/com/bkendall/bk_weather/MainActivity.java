package com.bkendall.bk_weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        double lat;
        double lon;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           //TODO add a screen to request permission
            return;
        }
        assert lm != null;

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location ==null){
            lat = 90.000;
            lon = 135.000;
        }
        else {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containter, new getWeatherFragment(lat, lon))
                    .commit();
        }
    }
}


