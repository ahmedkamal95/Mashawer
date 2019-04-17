package com.fekratoday.mashawer.model.beans;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Trip {
    private String name;
    private String startPoint;
    private String endPoint;
    private Date date;
    private Time time;
    private boolean oneWayTrip;
    private List<Note> notesList;

    public Trip(String name, String startPoint, String endPoint, Date date, Time time, boolean oneWayTrip, List<Note> notesList) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.date = date;
        this.time = time;
        this.oneWayTrip = oneWayTrip;
        this.notesList = notesList;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean isOneWayTrip() {
        return oneWayTrip;
    }

    public void setOneWayTrip(boolean oneWayTrip) {
        this.oneWayTrip = oneWayTrip;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public class Note{

        private String noteBody;
        private boolean doneState;

        public Note(String noteBody, boolean doneState) {
            this.noteBody = noteBody;
            this.doneState = doneState;
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

