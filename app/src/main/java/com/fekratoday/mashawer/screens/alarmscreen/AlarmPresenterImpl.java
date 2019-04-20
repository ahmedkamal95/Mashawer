package com.fekratoday.mashawer.screens.alarmscreen;

import android.app.NotificationManager;
import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.NotificationHelper;

public class AlarmPresenterImpl implements AlarmContract.Presenter {
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
    private AlarmContract.View view;
    private TripDaoSQL tripDaoSQL;

    AlarmPresenterImpl(AlarmContract.View view) {
        this.view = view;
        notificationHelper = new NotificationHelper((Context) view);
        notificationManager = notificationHelper.getManager();
        tripDaoSQL = new TripDaoSQL((Context) view);
    }

    @Override
    public void startTrip(Trip trip) {
        trip.setTripState(true);
        AlarmHelper.cancelAlarm((Context) view, trip.getId());
        notificationManager.cancel(trip.getId());
//        tripDaoSQL.
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
        tripDaoSQL.deleteTrip(tripId);
    }
}
