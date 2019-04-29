package com.fekratoday.mashawer.utilities;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.fekratoday.mashawer.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    /**
     * Setup BottomNavigationView
     *
     * @param bottomNavigationViewEx BottonNavigationView Variable
     */
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "BottomNavigationViewHelper: Setting up BottomNavigationView ");
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
    }

    /**
     * setOnClickListner BottomNavigationView
     *
     * @param viewPager  ViewPager Object
     * @param BtnNavView BottonNavigationView Object
     */
    public static void setOnClickBottomNavView(final ViewPager viewPager, BottomNavigationViewEx BtnNavView) {
        Log.d(TAG, "setOnClickBottomNavView: BottomNavigationItem Clicked");
        BtnNavView.setOnNavigationItemSelectedListener(item -> {
            Log.d(TAG, "setOnClickBottomNavView: " + item.getItemId());
            switch (item.getItemId()) {
                case R.id.navigation_upcoming:
                    item.setChecked(true);
                    viewPager.setCurrentItem(0); // Upcoming Trips Fragment
                    break;

                case R.id.navigation_history:
                    item.setChecked(true);
                    viewPager.setCurrentItem(1); // Trips History Fragment
                    break;

                case R.id.navigation_profile:
                    item.setChecked(true);
                    viewPager.setCurrentItem(2); // Profile Fragment
                    break;
            }
            return false;
        });
    }
}
