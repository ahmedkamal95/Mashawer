package com.fekratoday.mashawer.screens.alarmscreen;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.NotificationHelper;

public class AlarmActivity extends AppCompatActivity implements AlarmContract.View {

    private AlarmContract.Presenter presenter;
    private Builder alertBuilder;
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
    private MediaPlayer player;
    private Trip trip;
    private TripDaoSQL tripDaoSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setFinishOnTouchOutside(false);

        final int tripId = getIntent().getIntExtra("tripId", 0);
        Toast.makeText(this, tripId + "activity", Toast.LENGTH_SHORT).show();
        tripDaoSQL = new TripDaoSQL(this);
        trip = tripDaoSQL.getTripById(tripId);

        presenter = new AlarmPresenterImpl(this);

        player = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        player.setLooping(true);
        player.setVolume(100.0f, 100.0f);
        player.start();

        alertBuilder = new Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.app_name))
                .setMessage("Do you want to start " + trip.getName() + " trip?")
                .setPositiveButton("start", (dialogInterface, i) -> {
                    player.stop();
                    player.release();
                    presenter.startTrip(trip);
                    finish();
                }).setNeutralButton("snooze", (dialogInterface, i) -> {
            player.stop();
            player.release();
            presenter.snoozeTrip(trip, tripId);
            finish();
        }).setNegativeButton("Cancel Trip", (dialog, which) -> {
            player.stop();
            player.release();
            presenter.cancelTrip(trip, tripId);
            finish();
        }).setIcon(getResources().getDrawable(R.mipmap.ic_launcher)).setCancelable(false).show();
    }

    @Override
    public void showMap() {
        Intent mapIntent = new Intent("android.intent.action.VIEW",
                Uri.parse("http://maps.google.com/maps?saddr=" + trip.getStartPointLatitude() + "," + trip.getStartPointLongitude()
                        + "&daddr=" + trip.getEndPointLatitude() + "," + trip.getEndPointLongitude()));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(mapIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void startFloatingWidget() {

    }
}
