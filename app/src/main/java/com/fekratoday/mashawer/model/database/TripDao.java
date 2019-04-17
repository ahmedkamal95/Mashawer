package com.fekratoday.mashawer.model.database;

import com.fekratoday.mashawer.model.beans.Trip;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TripDao {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    String userId;

    public boolean insertTrip(Trip trip){
        boolean inserted = false;
        return  inserted;
    }
}
