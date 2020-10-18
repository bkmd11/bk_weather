package com.bkendall.bk_weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;


public class WeatherFragmentAdapter extends FragmentStateAdapter {
    String currentWeather;
    String hrByHr;

    // TODO: need to have this take all my strings
    //  Try a list/tuple type thing...
    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String current_weather, String hr_by_hr) {
        super(fragmentActivity);
        currentWeather = current_weather;
        hrByHr = hr_by_hr;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ShowWeatherFragment.newInstance(position, currentWeather, hrByHr, "bacon");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
