package com.fekratoday.mashawer.screens.addtripscreen;

import android.content.Context;

import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.NoteDaoSQL;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;

public class AddTripPresenterImpl implements AddTripContract {

    private TripDaoFirebase tripDaoFirebase;
    private TripDaoSQL tripDaoSQL;
    private NoteDaoSQL noteDaoSQL;

    AddTripPresenterImpl(Context context) {
        tripDaoFirebase = new TripDaoFirebase();
        tripDaoSQL = new TripDaoSQL(context);
        noteDaoSQL = new NoteDaoSQL(context);
    }

    @Override
    public void addTripFirebase(Trip trip) {
        tripDaoFirebase.insertTrip(trip);
    }

    @Override
    public int addTripSQLite(Trip trip) {
        return tripDaoSQL.insertTrip(trip);
    }

    @Override
    public void updateTripFirebase(Trip trip) {
        tripDaoFirebase.updateTrip(trip);
    }

    @Override
    public boolean updateTripSQLite(Trip trip) {
        return tripDaoSQL.updateTrip(trip);
    }

    @Override
    public void addNote(Trip.Note note, int tripId) {
        noteDaoSQL.insertNote(note,tripId);
    }

    @Override
    public void deleteNote(int noteId) {
        noteDaoSQL.deleteNote(noteId);
    }

    @Override
    public Trip getTrip(int tripId) {
        return tripDaoSQL.getTripById(tripId);
    }
}
