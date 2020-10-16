package com.bkendall.bk_weather;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;


public class WeatherFragmentAdapter extends FragmentStateAdapter {

    public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ShowWeatherFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
