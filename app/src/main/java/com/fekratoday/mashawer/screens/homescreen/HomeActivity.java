package com.fekratoday.mashawer.screens.homescreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.homescreen.fragments.ProfileFragment;
import com.fekratoday.mashawer.screens.homescreen.fragments.TripsHistoryFragment;
import com.fekratoday.mashawer.screens.homescreen.fragments.UpcomingTripsFragment;
import com.fekratoday.mashawer.screens.loginscreen.LoginActivity;
import com.fekratoday.mashawer.utilities.BottomNavigationViewHelper;
import com.fekratoday.mashawer.utilities.FmPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeCommunicator {

    private ViewPager viewPager;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private long time;
    private String username, email;
    private boolean check;
    private HomeContract presenter;
    private FragmentCommunicator upcomingCommunicator, tripHistoryCommunicator;
    private FmPagerAdapter adapter;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent commingIntent = getIntent();
        username = commingIntent.getStringExtra("username");
        email = commingIntent.getStringExtra("email");
        check = commingIntent.getBooleanExtra("check",false);
        presenter = new HomePresenterImpl(this);

        initViews();
        setupToolbar();
        setupFragmentAdapter(viewPager, bottomNavigationViewEx);
        setupBottomNavigationView(bottomNavigationViewEx, viewPager);

        upcomingCommunicator = (FragmentCommunicator) adapter.getItem(0);
        tripHistoryCommunicator = (FragmentCommunicator) adapter.getItem(1);

        if (check){
            presenter.getAllTripsData();
        }

        drawOverOtherPermission();
    }

    private void drawOverOtherPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }
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
        adapter = new FmPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingTripsFragment()); // Number 0
        adapter.addFragment(new TripsHistoryFragment()); // Number 1
        adapter.addFragment(new ProfileFragment()); // Number 2
        viewPager.setAdapter(adapter);
        /*viewPager.setPageTransformer(false, (view, position) -> {
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
        });*/
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void logout() {
        presenter.logout();
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public List<Trip> getAllUpcommingTrips() {
        return presenter.getAllUpcommingTrips();
    }

    @Override
    public List<Trip> getAllHistoryTrips() {
        return presenter.getAllHistoryTrips();
    }

    @Override
    public void setNotifyChange() {
        upcomingCommunicator.setNotifyChange();
        tripHistoryCommunicator.setNotifyChange();
    }

    @Override
    public void setAlarm() {
        presenter.setAlarm();
    }

    @Override
    public void sync() {
        presenter.sync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        upcomingCommunicator = null;
        tripHistoryCommunicator = null;
        presenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode != RESULT_OK) {
                drawOverOtherPermission();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
