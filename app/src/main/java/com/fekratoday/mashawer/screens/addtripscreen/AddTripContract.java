package com.fekratoday.mashawer.screens.addtripscreen;

import com.fekratoday.mashawer.model.beans.Trip;

public interface AddTripContract {

    void addTripFirebase(Trip trip);

    int addTripSQLite(Trip trip);

    void updateTripFirebase(Trip trip);

    boolean updateTripSQLite(Trip trip);

    void addNote(Trip.Note note, int tripId);

    void deleteNote(int noteId);

    Trip getTrip(int tripId);
}
