package com.fekratoday.mashawer.screens.homescreen;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public interface HomeCommunicator {

    String getUsername();

    String getEmail();

    void logout();

    void sync();

    void startLoginActivity();

    List<Trip> getAllUpcommingTrips();

    List<Trip> getAllHistoryTrips();

    void setNotifyChange();

    void setAlarm();
}
