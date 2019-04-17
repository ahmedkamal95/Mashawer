package com.fekratoday.mashawer.model.database;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public class TripDaoPresenterImpl implements TripDaoContract{

    TripDaoFirebase tripDaoFirebase;

    public TripDaoPresenterImpl(){
        tripDaoFirebase = new TripDaoFirebase();
    }
    @Override
    public boolean addTrip(Trip trip) {

        boolean inserted = tripDaoFirebase.insertTrip(trip);
        return inserted;
    }

    @Override
    public List<Trip> getallTrips() {

        List<Trip> tripList = tripDaoFirebase.getAllTrips();
        return tripList;
    }
}
