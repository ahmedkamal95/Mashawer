package com.fekratoday.mashawer.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    // Trip table
    public static final String TRIP_TABLE = "trip_table";
    public static final String TRIP_ID = "_id";
    public static final String NAME = "trip_name";
    public static final String START_POINT = "start_point";
    public static final String END_POINT = "end_point";
    public static final String TIME = "time";
    public static final String ONE_WAY_TRIP = "one_way_trip";
    public static final String REPEATED = "repeated";
    public static final String STARTED = "started";
    //Note table
    public static final String NOTE_TABLE = "note_table";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_BODY = "note_body";
    public static final String DONE_STATE = "done_state";
    public static  final String NOTE_TRIP_ID = "trip_id";

    private static final String DATABASE_NAME = "TripsData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TRIP = "CREATE TABLE "+ TRIP_TABLE + "("
            + TRIP_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME +" VARCHAR(255), "
            + START_POINT +" VARCHAR(255), "
            + END_POINT +" VARCHAR(255), "
            + TIME +" LONG, "
            + ONE_WAY_TRIP +" BOOLEAN, "
            + REPEATED +" BOOLEAN, "
            + STARTED +" BOOLEAN);";

    private static final String SQL_CREATE_TABLE_NOTE = "CREATE TABLE "+ NOTE_TABLE + "("
            + NOTE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_BODY +" VARCHAR(255), "
            + DONE_STATE +" BOOLEAN, "
            + NOTE_TRIP_ID +" INTEGER);";

    public MyDBHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TRIP);
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +NOTE_TABLE);
        onCreate(db);
    }
}
