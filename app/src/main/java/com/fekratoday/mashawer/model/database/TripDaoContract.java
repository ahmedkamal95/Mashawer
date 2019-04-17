package com.fekratoday.mashawer.model.database;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public interface TripDaoContract {

    public boolean addTrip(Trip trip);
    public List<Trip> getallTrips();

}
