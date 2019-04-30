package com.fekratoday.mashawer.screens.alarmscreen;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.fekratoday.mashawer.utilities.MapHelper;
import com.fekratoday.mashawer.utilities.NotificationHelper;

public class AlarmPresenterImpl implements AlarmContract.Presenter {
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
    private AlarmContract.View view;
    private TripDaoSQL tripDaoSQL;
    private TripDaoFirebase tripDaoFirebase;

    AlarmPresenterImpl(AlarmContract.View view) {
        this.view = view;
        notificationHelper = new NotificationHelper((Context) view);
        notificationManager = notificationHelper.getManager();
        tripDaoSQL = new TripDaoSQL((Context) view);
        tripDaoFirebase = new TripDaoFirebase();
    }

    @Override
    public void startTrip(Trip trip) {
        trip.setTripState(true);
        AlarmHelper.cancelAlarm((Context) view, trip.getId());
        notificationManager.cancel(trip.getId());
        tripDaoSQL.updateTrip(trip);
        if (CheckInternetConnection.getInstance((Activity) view).checkInternet()) {
            tripDaoFirebase.updateTrip(trip);
        }
//        view.showMap();
        new MapHelper((Context) view, trip).showMap();
    }

    @Override
    public void snoozeTrip(Trip trip) {
        AlarmHelper.cancelAlarm((Context) view, trip.getId());
        notificationHelper.createNotification(trip);
    }

    @Override
    public void cancelTrip(Trip trip) {
        notificationManager.cancel(trip.getId());
        AlarmHelper.cancelAlarm((Context) view, trip.getId());
        tripDaoSQL.deleteTrip(trip.getId());
        if (CheckInternetConnection.getInstance((Activity) view).checkInternet()) {
            tripDaoFirebase.deleteTrip(trip);
        }
    }

    @Override
    public Trip getTrip(int tripId) {
        return tripDaoSQL.getTripById(tripId);
    }
}
