package com.fekratoday.mashawer.screens.alarmscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AlarmContract {

    interface View {
        void showMap();

        void startFloatingWidget();
    }

    interface Presenter {
        void startTrip(Trip trip, int tripId);

        void snoozeTrip(Trip trip, int tripId);

        void cancelTrip(Trip trip, int tripId);
    }
}
