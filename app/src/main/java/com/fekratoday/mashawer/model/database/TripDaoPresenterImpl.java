package com.fekratoday.mashawer.model.database;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public class TripDaoPresenterImpl implements TripDaoContract{

    TripDao tripDao;

    public TripDaoPresenterImpl(){
        tripDao = new TripDao();
    }
    @Override
    public boolean addTrip(Trip trip) {

        boolean inserted = tripDao.insertTrip(trip);
        return inserted;
    }

    @Override
    public List<Trip> getallTrips() {

        List<Trip> tripList = tripDao.getAllTrips();
        return tripList;
    }
}
