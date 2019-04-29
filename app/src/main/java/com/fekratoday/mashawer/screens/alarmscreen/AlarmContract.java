package com.fekratoday.mashawer.screens.alarmscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AlarmContract {

    interface View {
//        void showMap();
    }

    interface Presenter {
        void startTrip(Trip trip);

        void snoozeTrip(Trip trip);

        void cancelTrip(Trip trip);

        Trip getTrip(int tripId);
    }
}
