package com.fekratoday.mashawer.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fekratoday.mashawer.model.beans.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripDaoSQL {

    private static final String PRENS_NAME = "UserData";
    private SharedPreferences userData;
    private SharedPreferences.Editor editor;
    private static final String TAG = "Trip Dao ";
    private SQLiteDatabase database;
    private MyDBHelper myDBHelper;
    private Context context;
    private String[] allColumns = {MyDBHelper.TRIP_ID, MyDBHelper.NAME,
            MyDBHelper.START_POINT, MyDBHelper.START_POINT_LATITUDE, MyDBHelper.START_POINT_LONGITUDE,
            MyDBHelper.END_POINT, MyDBHelper.END_POINT_LATITUDE, MyDBHelper.END_POINT_LONGITUDE,
            MyDBHelper.HOUR, MyDBHelper.MINUTE, MyDBHelper.DAY, MyDBHelper.MONTH,
            MyDBHelper.YEAR, MyDBHelper.ONE_WAY_TRIP, MyDBHelper.REPEATED, MyDBHelper.TRIP_STATE};

    public TripDaoSQL(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
        userData = context.getSharedPreferences(PRENS_NAME, Context.MODE_PRIVATE);
        editor = userData.edit();
    }

    private void open() {
        try {
            database = myDBHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void close() {
        myDBHelper.close();
    }

    public int insertTrip(Trip trip) {
        open();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NAME, trip.getName());
        values.put(MyDBHelper.START_POINT, trip.getStartPoint());
        values.put(MyDBHelper.START_POINT_LATITUDE, trip.getStartPointLatitude());
        values.put(MyDBHelper.START_POINT_LONGITUDE, trip.getStartPointLongitude());
        values.put(MyDBHelper.END_POINT, trip.getEndPoint());
        values.put(MyDBHelper.END_POINT_LATITUDE, trip.getEndPointLatitude());
        values.put(MyDBHelper.END_POINT_LONGITUDE, trip.getEndPointLongitude());
        values.put(MyDBHelper.HOUR, trip.getHour());
        values.put(MyDBHelper.MINUTE, trip.getMinute());
        values.put(MyDBHelper.DAY, trip.getDay());
        values.put(MyDBHelper.MONTH, trip.getMonth());
        values.put(MyDBHelper.YEAR, trip.getYear());
        values.put(MyDBHelper.ONE_WAY_TRIP, trip.isOneWayTrip());
        values.put(MyDBHelper.REPEATED, trip.isRepeated());
        values.put(MyDBHelper.TRIP_STATE, trip.isTripState());
        int id = (int) database.insert(MyDBHelper.TRIP_TABLE, null, values);
//        Cursor cursor = database.query(MyDBHelper.TRIP_TABLE,
//                allColumns, MyDBHelper.TRIP_ID + " = " + id, null, null, null, null);
        if (trip.getNotesList() != null) {
            if (!trip.getNotesList().isEmpty()) {
                NoteDaoSQL dao = new NoteDaoSQL(context);
                for (Trip.Note note : trip.getNotesList()) {
                    dao.insertNote(note);
                }
            }
        }
//        cursor.moveToFirst();
//        Trip newTrip = cursorToTrip(cursor);
//        cursor.close();

        /* Error when add trip */
//        editor.putInt("tripsCount", getAllTrips().size());
//        editor.commit();


        close();
        return id;
    }

    public boolean updateTrip(Trip trip) {
        open();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NAME, trip.getName());
        values.put(MyDBHelper.START_POINT, trip.getStartPoint());
        values.put(MyDBHelper.START_POINT_LATITUDE, trip.getStartPointLatitude());
        values.put(MyDBHelper.START_POINT_LONGITUDE, trip.getStartPointLongitude());
        values.put(MyDBHelper.END_POINT, trip.getEndPoint());
        values.put(MyDBHelper.END_POINT_LATITUDE, trip.getEndPointLatitude());
        values.put(MyDBHelper.END_POINT_LONGITUDE, trip.getEndPointLongitude());
        values.put(MyDBHelper.HOUR, trip.getHour());
        values.put(MyDBHelper.MINUTE, trip.getMinute());
        values.put(MyDBHelper.DAY, trip.getDay());
        values.put(MyDBHelper.MONTH, trip.getMonth());
        values.put(MyDBHelper.YEAR, trip.getYear());
        values.put(MyDBHelper.ONE_WAY_TRIP, trip.isOneWayTrip());
        values.put(MyDBHelper.REPEATED, trip.isRepeated());
        values.put(MyDBHelper.TRIP_STATE, trip.isTripState());
        int id = (int) database.update(MyDBHelper.TRIP_TABLE, values, MyDBHelper.TRIP_ID + " = ?",
                new String[]{String.valueOf(trip.getId())});
        if (trip.getNotesList() != null) {
            if (!trip.getNotesList().isEmpty()) {
                NoteDaoSQL dao = new NoteDaoSQL(context);
                for (Trip.Note note : trip.getNotesList()) {
                    dao.updateNote(note);
                }
            }
        }
        close();
        return id > -1;
    }

    public void deleteTrip(int tripId) {
        open();
        NoteDaoSQL noteDao = new NoteDaoSQL(context);
        List<Trip.Note> notesList = noteDao.getNotesOfTrip(tripId);
        if (notesList != null && !notesList.isEmpty()) {
            for (Trip.Note note : notesList) {
                noteDao.deleteNote(note.getId());
            }
        }
        database.delete(MyDBHelper.TRIP_TABLE, MyDBHelper.TRIP_ID + " = ?",
                new String[]{String.valueOf(tripId)});
        close();
    }

    public void deleteAllTrips(){
        for(Trip trip : getAllTrips()){
            deleteTrip(trip.getId());
        }
    }

    public List<Trip> getAllTrips() {
        open();
        List<Trip> tripsList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.TRIP_TABLE, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Trip trip = cursorToTrip(cursor);
                tripsList.add(trip);
                cursor.moveToNext();
            }
            cursor.close();
        }
        close();
        return tripsList;
    }

    public Trip getTripById(int tripId) {
        open();
        Trip trip = null;
        Cursor cursor = database.query(MyDBHelper.TRIP_TABLE, allColumns,
                MyDBHelper.TRIP_ID + " = ?",
                new String[]{String.valueOf(tripId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            trip = cursorToTrip(cursor);
            cursor.close();
        }
        close();
        return trip;
    }

    private Trip cursorToTrip(Cursor cursor) {
        Trip trip = new Trip();
        if (cursor != null && cursor.moveToFirst()) {
            int tripId = cursor.getInt(0);

            trip.setId(tripId);
            trip.setName(cursor.getString(1));
            trip.setStartPoint(cursor.getString(2));
            trip.setStartPointLatitude(cursor.getDouble(3));
            trip.setStartPointLongitude(cursor.getDouble(4));
            trip.setEndPoint(cursor.getString(5));
            trip.setEndPointLatitude(cursor.getDouble(6));
            trip.setEndPointLongitude(cursor.getDouble(7));
            trip.setHour(cursor.getInt(8));
            trip.setMinute(cursor.getInt(9));
            trip.setDay(cursor.getInt(10));
            trip.setMonth(cursor.getInt(11));
            trip.setYear(cursor.getInt(12));
            trip.setOneWayTrip(Boolean.parseBoolean(cursor.getString(13)));
            trip.setRepeated(Boolean.parseBoolean(cursor.getString(14)));
            trip.setTripState(Boolean.parseBoolean(cursor.getString(15)));

            NoteDaoSQL dao = new NoteDaoSQL(context);
            List<Trip.Note> notesList = dao.getNotesOfTrip(tripId);
            if (notesList != null) {
                trip.setNotesList(notesList);
            }
            cursor.close();
        }
        return trip;
    }
}
