package com.fekratoday.mashawer.model.database;

import com.fekratoday.mashawer.model.beans.Trip;

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
