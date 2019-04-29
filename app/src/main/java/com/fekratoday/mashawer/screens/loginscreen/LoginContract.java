package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.loginscreen.fragments.MainLoginFragment;
import com.fekratoday.mashawer.screens.loginscreen.fragments.SignupFragment;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface LoginContract {

    interface View {
        void login(FirebaseUser user);

        void toast(String message);

        void startActivityForResult(Intent data, int requestCode);

    }

    interface Presenter {
        void toast(String message);

        void login(FirebaseUser currentUser);

        void onButtonLoginClick(String email, String password);

        void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment);

        void startActivityForResult(Intent data, int requestCode);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onDestroy();

        void onButtonGoogleClick();

        void onButtonFacebookClick(LoginButton btnFacebook);

        FirebaseUser onStart();

        void getAllTripsData();

        void setTripList(List<Trip> allTrips);
    }
}
