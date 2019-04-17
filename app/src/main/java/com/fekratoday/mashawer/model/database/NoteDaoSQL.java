package com.fekratoday.mashawer.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fekratoday.mashawer.model.beans.Trip;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoSQL {

    public static final String TAG = "EmployeeDAO";

    private Context context;
    private SQLiteDatabase database;
    private MyDBHelper myDBHelper;
    private String[] allColumns = { MyDBHelper.NOTE_ID,
            MyDBHelper.NOTE_BODY,
            MyDBHelper.DONE_STATE,
            MyDBHelper.NOTE_TRIP_ID};

    public NoteDaoSQL(Context context) {
        myDBHelper = new MyDBHelper(context);
        this.context = context;

        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = myDBHelper.getWritableDatabase();
    }

    public void close() {
        myDBHelper.close();
    }

    public Trip.Note createNote(String noteBody, boolean done, int companyId) {

        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NOTE_BODY, noteBody);
        values.put(MyDBHelper.DONE_STATE, done);
        values.put(MyDBHelper.NOTE_TRIP_ID, companyId);
        long insertId = database
                .insert(MyDBHelper.NOTE_TABLE, null, values);
        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
                MyDBHelper.NOTE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Trip.Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public void deleteEmployee(Trip.Note note) {
        long id = note.getId();
        System.out.println("the deleted employee has the id: " + id);
        database.delete(MyDBHelper.NOTE_TABLE, MyDBHelper.NOTE_ID
                + " = " + id, null);
    }

    public List<Trip.Note> getAllEmployees() {
        List<Trip.Note> notesList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trip.Note note = cursorToNote(cursor);
            notesList.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notesList;
    }

    public List<Trip.Note> getNotesOfTrip(long tripId) {
        List<Trip.Note> notesList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
                MyDBHelper.NOTE_TRIP_ID + " = ?",
                new String[] { String.valueOf(tripId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trip.Note note = cursorToNote(cursor);
            notesList.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notesList;
    }

    private Trip.Note cursorToNote(Cursor cursor) {
        Trip.Note note = new Trip.Note();
        note.setId(cursor.getInt(0));
        note.setNoteBody(cursor.getString(1));
        note.setDoneState(Boolean.parseBoolean(cursor.getString(2)));
        note.setTripId(cursor.getInt(3));
        return note;
    }
}
