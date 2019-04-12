package com.fekratoday.mashawer.utilities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class FmPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FmPagerAdapter";

    private final List<Fragment> fragments = new ArrayList<>();

    public FmPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * Add New Fragment
     * @param fragment New Fragment
     */
    public void addFragment(Fragment fragment){
        Log.d(TAG, "addFragment: Fragment Added ");
        fragments.add(fragment);
    }

    /**
     * Handel Change in ViewPager
     * @param viewPager ViewPager Object
     * @param bottomNavigationViewEx BottomNavigationViewEX Object
     */
    public void onPageChange(ViewPager viewPager , BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "onPageChange: ViewPager Changed");
        final Menu menu = bottomNavigationViewEx.getMenu();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MenuItem menuItem = menu.getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
