package com.fekratoday.mashawer.screens.addtripscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AddTripContract {

    public boolean addTripFirebase(Trip trip);
    public boolean addTripSQLite(Trip trip);
}
