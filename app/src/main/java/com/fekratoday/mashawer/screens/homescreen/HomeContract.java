package com.fekratoday.mashawer.screens.homescreen;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public interface HomeContract {
    void logout();

    void sync();

    List<Trip> getAllUpcommingTrips();

    List<Trip> getAllHistoryTrips();

    void uploadTripToFirebase();

    void getAllTripsData();

    void setTripList(List<Trip> allTrips);

    void onDestroy();

    void setAlarm();
}
