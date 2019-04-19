package com.fekratoday.mashawer.screens.alarmscreen;

import android.app.NotificationManager;
import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.NotificationHelper;

public class AlarmPresenterImpl implements AlarmContract.Presenter {
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
    private AlarmContract.View view;

    AlarmPresenterImpl(AlarmContract.View view) {
        this.view = view;
        notificationHelper = new NotificationHelper((Context) view);
        notificationManager = notificationHelper.getManager();
    }

    @Override
    public void startTrip(Trip trip, int tripId) {
//        trip.setTripStatus("started");
//        trip.setId(tripId);
        AlarmHelper.cancelAlarm((Context) view, tripId);
        notificationManager.cancel(tripId);
        view.showMap();
        view.startFloatingWidget();
    }

    @Override
    public void snoozeTrip(Trip trip, int tripId) {
        AlarmHelper.cancelAlarm((Context) view, tripId);
        notificationHelper.createNotification(trip);
    }

    @Override
    public void cancelTrip(Trip trip, int tripId) {
        notificationManager.cancel(tripId);
        AlarmHelper.cancelAlarm((Context) view, tripId);
    }
}
