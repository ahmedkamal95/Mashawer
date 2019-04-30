package com.fekratoday.mashawer.screens.homescreen;

import android.app.Activity;
import android.content.Context;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImpl implements HomeContract {

    private HomeCommunicator communicator;
    private TripDaoSQL tripDaoSQL;
    private TripDaoFirebase tripDaoFirebase;
    private static int sqlListCount;

    HomePresenterImpl(HomeCommunicator communicator) {
        this.communicator = communicator;
        tripDaoSQL = new TripDaoSQL((Context) communicator);
        tripDaoFirebase = new TripDaoFirebase();
    }

    @Override
    public void logout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(((Activity) communicator).getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient((Activity) communicator, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        communicator.startLoginActivity();
    }

    @Override
    public void sync() {
        tripDaoFirebase.setHomePresenter(this);
        tripDaoFirebase.deleteAllTrips();
    }

    @Override
    public void uploadTripToFirebase(){
        List<Trip> tripList = tripDaoSQL.getAllTrips();
        for (Trip trip: tripList){
            tripDaoFirebase.insertTrip(trip);
        }
    }

    @Override
    public void getAllTripsData() {
        tripDaoSQL.deleteAllTrips();
        sqlListCount=0;
        if (CheckInternetConnection.getInstance((Context) communicator).checkInternet()) {
            tripDaoFirebase.setHomePresenter(this);
            tripDaoFirebase.check = true;
            tripDaoFirebase.getAllTrips();
        }
    }

    @Override
    public void setTripList(List<Trip> allTrips) {
        if(tripDaoSQL.getAllTrips().size()< allTrips.size()) {

            for (int i= sqlListCount; i<allTrips.size(); i++) {
                tripDaoSQL.insertTrip(allTrips.get(i));

            }
            sqlListCount = tripDaoSQL.getAllTrips().size();
            communicator.setNotifyChange();
        }
    }

    @Override
    public void onDestroy() {
        communicator = null;
        tripDaoFirebase.onDestroy();
        tripDaoSQL.onDestroy();
    }

    @Override
    public List<Trip> getAllUpcommingTrips() {
        return tripDaoSQL.getAllUpcommingTrips();
    }

    @Override
    public List<Trip> getAllHistoryTrips() {
        return tripDaoSQL.getAllHistoryTrips();
    }


}
