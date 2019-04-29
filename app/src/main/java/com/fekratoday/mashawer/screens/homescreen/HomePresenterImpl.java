package com.fekratoday.mashawer.screens.homescreen;

import android.app.Activity;
import android.content.Context;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
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
        sync();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(((Activity) communicator).getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient((Activity) communicator, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        List<Trip> trips = tripDaoSQL.getAllTrips();
        for (Trip trip : trips) {
            AlarmHelper.cancelAlarm((Context) communicator, trip.getId());
        }
        communicator.startLoginActivity();
    }

    @Override
    public void sync() {
        if (CheckInternetConnection.getInstance((Context) communicator).checkInternet()) {
            tripDaoFirebase.setHomePresenter(this);
            tripDaoFirebase.deleteAllTrips();
        }
    }

    @Override
    public void uploadTripToFirebase() {
        List<Trip> tripList = tripDaoSQL.getAllTrips();
        for (Trip trip : tripList) {
            tripDaoFirebase.insertTrip(trip);
        }
    }

    @Override
    public void getAllTripsData() {
        tripDaoSQL.deleteAllTrips();
        sqlListCount = 0;
        if (CheckInternetConnection.getInstance((Context) communicator).checkInternet()) {
            tripDaoFirebase.setHomePresenter(this);
            tripDaoFirebase.check = true;
            tripDaoFirebase.getAllTrips();
        }
    }

    @Override
    public void setTripList(List<Trip> allTrips) {
        sqlListCount = tripDaoSQL.getAllTrips().size();
        if (sqlListCount < allTrips.size()) {
            for (int i = sqlListCount; i < allTrips.size(); i++) {
                tripDaoSQL.insertTrip(allTrips.get(i));
            }
            setAlarm();
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
    public void setAlarm() {
        List<Trip> trips = getAllUpcommingTrips();
        if (!trips.isEmpty()) {
            for (Trip trip : trips) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, trip.getYear());
                calendar.set(Calendar.MONTH, trip.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, trip.getDay());
                calendar.set(Calendar.HOUR_OF_DAY, trip.getHour());
                calendar.set(Calendar.MINUTE, trip.getMinute());
                if (calendar.before(Calendar.getInstance())) {
                    tripDaoSQL.deleteTrip(trip.getId());
                    if (CheckInternetConnection.getInstance((Context) communicator).checkInternet()){
                        tripDaoFirebase.deleteTrip(trip);
                    }
                } else {
                    AlarmHelper.setAlarm((Context) communicator, trip.getId(), calendar);
                }
            }
        }
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
