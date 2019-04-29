package com.fekratoday.mashawer.screens.alarmscreen;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;

import java.util.List;

public class AlarmActivity extends AppCompatActivity implements AlarmContract.View {

    private AlarmContract.Presenter presenter;
    private MediaPlayer player;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        presenter = new AlarmPresenterImpl(this);
        final int tripId = getIntent().getIntExtra("tripId", 0);
        trip = presenter.getTrip(tripId);

        setFinishOnTouchOutside(false);
        startMediaPlayer();
        showAlertDialog();
    }

    private void showAlertDialog() {
        Builder alertBuilder = new Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.app_name))
                .setMessage("Do you want to start " + trip.getName() + " trip?")
                .setPositiveButton("Start", (dialogInterface, i) -> {
                    stopMediaPlayer();
                    presenter.startTrip(trip);
                    finish();
                })
                .setNeutralButton("Snooze", (dialogInterface, i) -> {
                    stopMediaPlayer();
                    presenter.snoozeTrip(trip);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    stopMediaPlayer();
                    presenter.cancelTrip(trip);
                    finish();
                })
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setCancelable(false)
                .show();
    }

    private void startMediaPlayer() {
        player = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        player.setLooping(true);
        player.setVolume(100.0f, 100.0f);
        player.start();
    }

    private void stopMediaPlayer() {
        player.stop();
        player.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMediaPlayer();
        presenter.snoozeTrip(trip);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
