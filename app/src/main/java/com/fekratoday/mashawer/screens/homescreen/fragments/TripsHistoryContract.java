package com.fekratoday.mashawer.screens.homescreen.fragments;

import com.fekratoday.mashawer.model.beans.Trip;
import java.util.List;

public interface TripsHistoryContract {

    public void deleteTripFirebase(Trip trip);
    public void deleteTripSQLite(Trip trip);
    public List<Trip> getallTripsFirebase();
    public List<Trip> getallTripsSQLite();
}
