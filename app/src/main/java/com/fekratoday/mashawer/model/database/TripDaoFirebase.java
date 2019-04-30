package com.fekratoday.mashawer.model.database;


import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.homescreen.HomeContract;
import com.fekratoday.mashawer.screens.loginscreen.LoginContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripDaoFirebase {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String userId;
    private LoginContract.Presenter loginPresenter;
    private HomeContract homePresenter;
    public boolean check = false;

    public TripDaoFirebase() {
    }

    public void setLoginPresenter(LoginContract.Presenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void setHomePresenter(HomeContract homePresenter) {
        this.homePresenter = homePresenter;
    }

    public void insertTrip(Trip trip) {
        check = false;
        getDatabaseRef();
        myRef.child("trip" + trip.getId()).setValue(trip);
    }

    public void updateTrip(Trip trip) {
        check = false;
        getDatabaseRef();
        myRef.child("trip" + trip.getId()).setValue(trip);
    }

    public void deleteTrip(Trip trip) {
        check = false;
        getDatabaseRef();
        myRef.child("trip" + trip.getId()).removeValue();

    }

    public void getAllTrips() {
        getDatabaseRef();
        List<Trip> allTrips = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (check) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        allTrips.add(snapshot.getValue(Trip.class));
                    }
                    homePresenter.setTripList(allTrips);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void deleteAllTrips() {
        check = false;
        getDatabaseRef();
        myRef.removeValue();
        homePresenter.uploadTripToFirebase();
    }

    public void updateNote(Trip.Note note, int position){
        getDatabaseRef();
        myRef.child("trip" + note.getTripId()).child("notesList").child(String.valueOf(position)).setValue(note);
    }

    private void getDatabaseRef() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(userId).child("trips");
    }

    public void onDestroy() {
        loginPresenter = null;
        homePresenter = null;
    }
}
