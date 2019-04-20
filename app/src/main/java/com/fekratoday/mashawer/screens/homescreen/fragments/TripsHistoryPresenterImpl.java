package com.fekratoday.mashawer.screens.homescreen.fragments;

import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;

import java.util.List;

public class TripsHistoryPresenterImpl implements TripsHistoryContract {

    TripDaoFirebase tripDaoFirebase;
    TripDaoSQL tripDaoSQL;

    public TripsHistoryPresenterImpl(Context context){
        tripDaoFirebase = new TripDaoFirebase();
        tripDaoSQL = new TripDaoSQL(context);
    }

    @Override
    public void deleteTripFirebase(Trip trip) {

    }

    @Override
    public void deleteTripSQLite(Trip trip) {

        tripDaoSQL.deleteTrip(trip.getId());
    }

    @Override
    public List<Trip> getallTripsFirebase() {

        List<Trip> tripList = tripDaoFirebase.getAllTrips();
        return tripList;
    }

    @Override
    public List<Trip> getallTripsSQLite() {

        List<Trip> tripList = tripDaoSQL.getAllTrips();
        return tripList;
    }
}
