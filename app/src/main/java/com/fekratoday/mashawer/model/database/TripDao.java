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

public class TripDao {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    String userId;

    public TripDao(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(userId).child("trips");
    }

    public boolean insertTrip(Trip trip){

        boolean inserted = false;
        myRef.child(myRef.push().getKey()).setValue(trip);
        return  inserted;
    }

    public List<Trip> getAllTrips(){

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
}
