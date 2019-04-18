package com.fekratoday.mashawer.screens.addtripscreen;

import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.NoteDaoSQL;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;

public class AddTripPresenterImpl implements AddTripContract {

    TripDaoFirebase tripDaoFirebase;
    TripDaoSQL tripDaoSQL;
    NoteDaoSQL noteDaoSQL;

    public AddTripPresenterImpl(Context context){
        tripDaoFirebase = new TripDaoFirebase();
        tripDaoSQL = new TripDaoSQL(context);
        noteDaoSQL = new NoteDaoSQL(context);
    }

    @Override
    public boolean addTripFirebase(Trip trip) {

        boolean inserted = tripDaoFirebase.insertTrip(trip);
        return inserted;
    }

    @Override
    public boolean addTripSQLite(Trip trip) {
        int tripId = tripDaoSQL.insertTrip(trip.getName(), trip.getStartPoint(), trip.getEndPoint(), trip.getTime(),
                trip.getOneWayTrip(), trip.getRepeated(), trip.getStarted());
        for(Trip.Note note: trip.getNotesList()){
            noteDaoSQL.insertNote(note.getNoteBody(), note.getDoneState(), tripId);
        }
        return tripId>-1;
    }
}
