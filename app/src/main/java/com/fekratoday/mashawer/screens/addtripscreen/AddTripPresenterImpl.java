package com.fekratoday.mashawer.screens.addtripscreen;

import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;

public class AddTripPresenterImpl implements AddTripContract {

    TripDaoFirebase tripDaoFirebase;
    TripDaoSQL tripDaoSQL;

    public AddTripPresenterImpl(Context context){
        tripDaoFirebase = new TripDaoFirebase();
        tripDaoSQL = new TripDaoSQL(context);
    }

    @Override
    public void addTripFirebase(Trip trip) {

        tripDaoFirebase.insertTrip(trip);
//        return inserted;
    }

    @Override
    public boolean addTripSQLite(Trip trip) {

        int tripId = tripDaoSQL.insertTrip(trip);
        return tripId>-1;
    }
}
