package com.fekratoday.mashawer.screens.addtripscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AddTripContract {

    public void addTripFirebase(Trip trip);
    public boolean addTripSQLite(Trip trip);
    public void updateTripFirebase(Trip trip);
    public boolean updateTripSQLite(Trip trip);
}
