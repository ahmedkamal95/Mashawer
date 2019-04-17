package com.fekratoday.mashawer.model.beans;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Trip {

    private int id;
    private String name;
    private String startPoint;
    private String endPoint;
    private long time;
    private boolean oneWayTrip;
    private boolean repeated;
    private boolean started;
    private List<Note> notesList;

    public  Trip(){

    }
    public Trip(int id, String name, String startPoint, String endPoint, long time, boolean oneWayTrip, boolean repeated, boolean started, List<Note> notesList) {
        this.id = id;
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
        this.oneWayTrip = oneWayTrip;
        this.repeated = repeated;
        this.started = started;
        this.notesList = notesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean getOneWayTrip() {
        return oneWayTrip;
    }

    public void setOneWayTrip(boolean oneWayTrip) {
        this.oneWayTrip = oneWayTrip;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public static class Note{

        private int id;
        private String noteBody;
        private boolean doneState;
        private int tripId;

        public  Note(){

        }

        public Note(int id, String noteBody, boolean doneState, int tripId) {
            this.id = id;
            this.noteBody = noteBody;
            this.doneState = doneState;
            this.tripId = tripId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTripId() {
            return tripId;
        }

        public void setTripId(int tripId) {
            this.tripId = tripId;
        }

        public String getNoteBody() {
            return noteBody;
        }

        public void setNoteBody(String noteBody) {
            this.noteBody = noteBody;
        }

        public boolean getDoneState() {
            return doneState;
        }

        public void setDoneState(boolean doneState) {
            this.doneState = doneState;
        }

    }
}

