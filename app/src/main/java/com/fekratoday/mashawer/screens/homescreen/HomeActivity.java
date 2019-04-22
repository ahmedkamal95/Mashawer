package com.fekratoday.mashawer.screens.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.homescreen.fragments.ProfileFragment;
import com.fekratoday.mashawer.screens.homescreen.fragments.TripsHistoryFragment;
import com.fekratoday.mashawer.screens.homescreen.fragments.UpcomingTripsFragment;
import com.fekratoday.mashawer.utilities.BottomNavigationViewHelper;
import com.fekratoday.mashawer.utilities.FmPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private long time;
    private String username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent commingIntent = getIntent();
        username = commingIntent.getStringExtra("username");
        email = commingIntent.getStringExtra("email");

        initViews();
        setupToolbar();
        setupFragmentAdapter(viewPager, bottomNavigationViewEx);
        setupBottomNavigationView(bottomNavigationViewEx, viewPager);
    }

    /**
     * Initialize Views
     */
    private void initViews() {
        viewPager = findViewById(R.id.container);
        bottomNavigationViewEx = findViewById(R.id.bottomNavView);
    }

    /**
     * Setting Toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
    }

    /**
     * Setting Fragment Pager Adapter
     *
     * @param viewPager        ViewPager Object
     * @param navigationViewEx BottomNavigationEx Object
     */
    private void setupFragmentAdapter(ViewPager viewPager, BottomNavigationViewEx navigationViewEx) {
        FmPagerAdapter adapter = new FmPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingTripsFragment()); // Number 0
        adapter.addFragment(new TripsHistoryFragment()); // Number 1
        adapter.addFragment(new ProfileFragment()); // Number 2
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, (view, position) -> {
            if (position <= -1.0F || position >= 1.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(0.0F);
            } else if (position == 0.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setTranslationX(view.getWidth() * -position);
                view.setAlpha(1.0F - Math.abs(position));
            }
        });
        adapter.onPageChange(viewPager, navigationViewEx);
    }

    /**
     * Setup BottomNavigationView
     *
     * @param viewEx BottomNavigationEx Object
     * @param pager  ViewPager Object
     */
    private void setupBottomNavigationView(BottomNavigationViewEx viewEx, ViewPager pager) {
        BottomNavigationViewHelper.setupBottomNavigationView(viewEx);
        BottomNavigationViewHelper.setOnClickBottomNavView(pager, viewEx);
    }


    /**
     * Handle Backpressed to close app
     */
    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(HomeActivity.this, "Press again to close", Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }
}
