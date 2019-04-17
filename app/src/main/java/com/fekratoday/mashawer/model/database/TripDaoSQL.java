package com.fekratoday.mashawer.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fekratoday.mashawer.model.beans.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripDaoSQL {

    private static final String TAG = "Trip Dao ";
    private SQLiteDatabase database;
    private MyDBHelper myDBHelper;
    private Context context;
    private String[] allColumns = { MyDBHelper.TRIP_ID, MyDBHelper.NAME, MyDBHelper.START_POINT,
            MyDBHelper.END_POINT, MyDBHelper.TIME, MyDBHelper.ONE_WAY_TRIP,
            MyDBHelper.REPEATED};

    public TripDaoSQL(Context context){
        this.context = context;
        myDBHelper = new MyDBHelper(context);
        try{
            open();
        }catch (SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void open() throws SQLException{
        database = myDBHelper.getWritableDatabase();
    }

    public void close(){
        myDBHelper.close();
    }

    public Trip insertTrip(String name, String startPoint, String endPoint, long time, boolean oneWayTrip,
                           boolean repeated, boolean started){

        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NAME, name);
        values.put(MyDBHelper.START_POINT, startPoint);
        values.put(MyDBHelper.END_POINT, endPoint);
        values.put(MyDBHelper.TIME, time);
        values.put(MyDBHelper.ONE_WAY_TRIP, oneWayTrip);
        values.put(MyDBHelper.REPEATED, repeated);
        values.put(MyDBHelper.STARTED, started);
        long id = database.insert(MyDBHelper.TRIP_TABLE, null , values);
        Cursor cursor = database.query(MyDBHelper.TRIP_TABLE,
                allColumns, MyDBHelper.TRIP_ID +" = "+ id , null, null, null, null);
        cursor.moveToFirst();
        Trip newTrip = cursorToTrip(cursor);
        cursor.close();
        return newTrip;
    }

    public void deleteTrip(Trip trip) {
        long id = trip.getId();
        NoteDaoSQL noteDao = new NoteDaoSQL(context);
        List<Trip.Note> notesList = noteDao.getNotesOfTrip(id);
        if (notesList != null && !notesList.isEmpty()) {
            for (Trip.Note note : notesList) {
                noteDao.deleteEmployee(note);
            }
        }

        System.out.println("the deleted company has the id: " + id);
        database.delete(MyDBHelper.TRIP_TABLE, MyDBHelper.TRIP_ID
                + " = " + id, null);
    }

    public List<Trip> getAllCompanies() {
        List<Trip> tripsList = new ArrayList<Trip>();

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
        return tripsList;
    }

    public Trip getTripById(long id) {
        Cursor cursor = database.query(MyDBHelper.TRIP_TABLE, allColumns,
                MyDBHelper.TRIP_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Trip trip = cursorToTrip(cursor);
        return trip;
    }

    protected Trip cursorToTrip(Cursor cursor) {
        Trip trip = new Trip();
        trip.setId(cursor.getInt(0));
        trip.setName(cursor.getString(1));
        trip.setStartPoint(cursor.getString(2));
        trip.setEndPoint(cursor.getString(3));
        trip.setTime(cursor.getLong(4));
        trip.setOneWayTrip(Boolean.parseBoolean(cursor.getString(5)));
        trip.setRepeated(Boolean.parseBoolean(cursor.getString(6)));
        trip.setStarted(Boolean.parseBoolean(cursor.getString(7)));
        int tripId = cursor.getInt(0);
        NoteDaoSQL dao = new NoteDaoSQL(context);
        List<Trip.Note> notesList = dao.getNotesOfTrip(tripId);
        if (notesList != null)
            trip.setNotesList(notesList);
        return trip;
    }
}
