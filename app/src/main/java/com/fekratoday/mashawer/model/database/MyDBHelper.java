package com.fekratoday.mashawer.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    // Trip table
    static final String TRIP_TABLE = "trip_table";
    static final String TRIP_ID = "_id";
    static final String NAME = "trip_name";
    static final String START_POINT = "start_point";
    static final String START_POINT_LATITUDE = "start_point_latitude";
    static final String START_POINT_LONGITUDE = "start_point_longitude";
    static final String END_POINT = "end_point";
    static final String END_POINT_LATITUDE = "end_point_latitude";
    static final String END_POINT_LONGITUDE = "end_point_longitude";
    static final String HOUR = "hour";
    static final String MINUTE = "minute";
    static final String DAY = "day";
    static final String MONTH = "month";
    static final String YEAR = "year";
    static final String ONE_WAY_TRIP = "one_way_trip";
    static final String REPEATED = "repeated";
    static final String TRIP_STATE = "trip_state";

    //Note table
    static final String NOTE_TABLE = "note_table";
    static final String NOTE_ID = "_id";
    static final String NOTE_BODY = "note_body";
    static final String DONE_STATE = "done_state";
    static final String NOTE_TRIP_ID = "trip_id";

    private static final String DATABASE_NAME = "TripsData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TRIP = "CREATE TABLE " + TRIP_TABLE + "("
            + TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(255), "
            + START_POINT + " VARCHAR(255), "
            + START_POINT_LATITUDE + " REAL, "
            + START_POINT_LONGITUDE + " REAL, "
            + END_POINT + " VARCHAR(255), "
            + END_POINT_LATITUDE + " REAL, "
            + END_POINT_LONGITUDE + " REAL, "
            + HOUR + " INTEGER, "
            + MINUTE + " INTEGER, "
            + DAY + " INTEGER, "
            + MONTH + " INTEGER, "
            + YEAR + " INTEGER, "
            + ONE_WAY_TRIP + " INTEGER, "
            + REPEATED + " INTEGER, "
            + TRIP_STATE + " INTEGER);";

    private static final String SQL_CREATE_TABLE_NOTE = "CREATE TABLE " + NOTE_TABLE + "("
            + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_BODY + " VARCHAR(255), "
            + DONE_STATE + " INTEGER, "
            + NOTE_TRIP_ID + " INTEGER);";

    MyDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TRIP);
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        onCreate(db);
    }
}
