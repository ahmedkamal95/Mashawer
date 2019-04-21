package com.fekratoday.mashawer.screens.addtripscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AddTripContract {

    void addTripFirebase(Trip trip);

    boolean addTripSQLite(Trip trip);

    void updateTripFirebase(Trip trip);

    boolean updateTripSQLite(Trip trip);
}
