package com.fekratoday.mashawer.model.database;


import com.fekratoday.mashawer.model.beans.Trip;
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

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    String userId;

    public TripDaoFirebase() {

    }

    public void insertTrip(Trip trip) {
        getDatabaseRef();
        boolean inserted = false;
        myRef.child("trip"+trip.getId()).setValue(trip);
//        return inserted;
    }

    public void updateTrip(Trip trip) {
        getDatabaseRef();
        boolean inserted = false;
        myRef.child("trip"+trip.getId()).setValue(trip);
//        return inserted;
    }

    public void deleteTrip(Trip trip) {
        getDatabaseRef();
        myRef.child("trip"+trip.getId()).removeValue();

    }

    public List<Trip> getAllTrips() {

        getDatabaseRef();
        List<Trip> allTrips = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    allTrips.add(snapshot.getValue(Trip.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allTrips;
    }

    private void getDatabaseRef(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(userId).child("trips");
    }
}
