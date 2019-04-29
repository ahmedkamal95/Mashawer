package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Context;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;
import com.fekratoday.mashawer.screens.loginscreen.fragments.SignupFragment;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginServicesInterface loginServices;
    private TripDaoSQL tripDaoSQL;
    private TripDaoFirebase tripDaoFirebase;

    LoginPresenterImpl(LoginContract.View view) {
        this.view = view;
        loginServices = new LoginServices(view, this);
        tripDaoSQL = new TripDaoSQL((Context) view);
        tripDaoFirebase = new TripDaoFirebase();
    }

    @Override
    public void toast(String message) {
        view.toast(message);
    }


    @Override
    public void login(FirebaseUser user) {
        view.login(user);
    }

    @Override
    public void onButtonLoginClick(String email, String password) {
        loginServices.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment) {
        loginServices.createUserWithEmailAndPassword(email, password, userName);
    }

    @Override
    public void startActivityForResult(Intent data, int requestCode) {
        view.startActivityForResult(data, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginServices.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onButtonGoogleClick() {
        loginServices.signInWithGoogle();
    }

    @Override
    public void onButtonFacebookClick(LoginButton btnFacebook) {
        loginServices.signInWithFacebook(btnFacebook);
    }

    @Override
    public FirebaseUser onStart() {
        return loginServices.isLoggedIn();
    }

    @Override
    public void getAllTripsData() {
        tripDaoSQL.deleteAllTrips();

        if (CheckInternetConnection.getInstance((Context) view).checkInternet()) {
            tripDaoFirebase.setLoginPresenter(this);
            tripDaoFirebase.check = true;
            tripDaoFirebase.getAllTrips();
        }
    }

    @Override
    public void setTripList(List<Trip> allTrips) {
        for (Trip trip : allTrips) {
            tripDaoSQL.insertTrip(trip);
        }
    }

    @Override
    public void onDestroy() {
        loginServices.onDestroy();
        view = null;
        loginServices = null;
    }
}
