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

public class NoteDaoSQL {

    private static final String TAG = "EmployeeDAO";

    private Context context;
    private SQLiteDatabase database;
    private MyDBHelper myDBHelper;
    private String[] allColumns = {MyDBHelper.NOTE_ID,
            MyDBHelper.NOTE_BODY,
            MyDBHelper.DONE_STATE,
            MyDBHelper.NOTE_TRIP_ID};

    NoteDaoSQL(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
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

    int insertNote(Trip.Note note) {
        open();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NOTE_BODY, note.getNoteBody());
        values.put(MyDBHelper.DONE_STATE, note.getDoneState());
        values.put(MyDBHelper.NOTE_TRIP_ID, note.getTripId());
        int id = (int) database.insert(MyDBHelper.NOTE_TABLE, null, values);
//        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
//                MyDBHelper.NOTE_ID + " = " + insertId, null, null,
//                null, null);
//        cursor.moveToFirst();
//        Trip.Note newNote = cursorToNote(cursor);
//        cursor.close();
        close();
        return id;
    }

    boolean updateNote(Trip.Note note){
        open();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.NOTE_BODY, note.getNoteBody());
        values.put(MyDBHelper.DONE_STATE, note.getDoneState());
        values.put(MyDBHelper.NOTE_TRIP_ID, note.getTripId());
        int id = (int) database.update(MyDBHelper.NOTE_TABLE, values, MyDBHelper.NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
//        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
//                MyDBHelper.NOTE_ID + " = " + insertId, null, null,
//                null, null);
//        cursor.moveToFirst();
//        Trip.Note newNote = cursorToNote(cursor);
//        cursor.close();
        close();
        return id > -1;
    }

    void deleteNote(int noteID) {
        open();
        database.delete(MyDBHelper.NOTE_TABLE, MyDBHelper.NOTE_ID
                + " = ?", new String[]{String.valueOf(noteID)});
        close();
    }

    List<Trip.Note> getAllNotes() {
        open();
        List<Trip.Note> notesList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Trip.Note note = cursorToNote(cursor);
                notesList.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        close();
        return notesList;
    }

    List<Trip.Note> getNotesOfTrip(int tripId) {
        open();
        List<Trip.Note> notesList = new ArrayList<>();

        Cursor cursor = database.query(MyDBHelper.NOTE_TABLE, allColumns,
                MyDBHelper.NOTE_TRIP_ID + " = ?",
                new String[]{String.valueOf(tripId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Trip.Note note = cursorToNote(cursor);
                notesList.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        close();
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
