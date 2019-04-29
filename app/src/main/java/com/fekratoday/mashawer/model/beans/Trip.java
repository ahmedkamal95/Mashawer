package com.fekratoday.mashawer.model.beans;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {

    private int id;
    private String name;
    private String startPoint;
    private double startPointLatitude;
    private double startPointLongitude;
    private String endPoint;
    private double endPointLatitude;
    private double endPointLongitude;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private boolean oneWayTrip;
    private boolean repeated;
    private boolean tripState;
    private List<Note> notesList;

    public Trip() {
    }

    public Trip(int id, String name, String startPoint, double startPointLatitude, double startPointLongitude,
                String endPoint, double endPointLatitude, double endPointLongitude, int hour, int minute, int day,
                int month, int year, boolean oneWayTrip, boolean repeated, boolean tripState, List<Note> notesList) {
        this.id = id;
        this.name = name;
        this.startPoint = startPoint;
        this.startPointLatitude = startPointLatitude;
        this.startPointLongitude = startPointLongitude;
        this.endPoint = endPoint;
        this.endPointLatitude = endPointLatitude;
        this.endPointLongitude = endPointLongitude;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.oneWayTrip = oneWayTrip;
        this.repeated = repeated;
        this.tripState = tripState;
        this.notesList = notesList;
    }

    public void setAll(Trip trip){
        this.id = trip.getId();
        this.name = trip.getName();
        this.startPoint = trip.getStartPoint();
        this.startPointLatitude = trip.getStartPointLatitude();
        this.startPointLongitude = trip.getStartPointLongitude();
        this.endPoint = trip.getEndPoint();
        this.endPointLatitude = trip.getEndPointLatitude();
        this.endPointLongitude = trip.getEndPointLongitude();
        this.hour = trip.getHour();
        this.minute = trip.getMinute();
        this.day = trip.getDay();
        this.month = trip.getMonth();
        this.year = trip.getYear();
        this.oneWayTrip = trip.isOneWayTrip();
        this.repeated = trip.isRepeated();
        this.tripState = trip.isTripState();
        this.notesList = trip.getNotesList();
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

    public double getStartPointLatitude() {
        return startPointLatitude;
    }

    public void setStartPointLatitude(double startPointLatitude) {
        this.startPointLatitude = startPointLatitude;
    }

    public double getStartPointLongitude() {
        return startPointLongitude;
    }

    public void setStartPointLongitude(double startPointLongitude) {
        this.startPointLongitude = startPointLongitude;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public double getEndPointLatitude() {
        return endPointLatitude;
    }

    public void setEndPointLatitude(double endPointLatitude) {
        this.endPointLatitude = endPointLatitude;
    }

    public double getEndPointLongitude() {
        return endPointLongitude;
    }

    public void setEndPointLongitude(double endPointLongitude) {
        this.endPointLongitude = endPointLongitude;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isOneWayTrip() {
        return oneWayTrip;
    }

    public void setOneWayTrip(boolean oneWayTrip) {
        this.oneWayTrip = oneWayTrip;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean isTripState() {
        return tripState;
    }

    public void setTripState(boolean tripState) {
        this.tripState = tripState;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public static class Note implements Serializable{

        private int id;
        private String noteBody;
        private boolean doneState;
        private int tripId;

        public Note() {

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

