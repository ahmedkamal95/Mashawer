package com.fekratoday.mashawer.model.networkservices;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseUser;

public interface LoginServicesInterface {
    void signInWithEmailAndPassword(String email, String password);

    void createUserWithEmailAndPassword(String email, String password, String userName);

    void signInWithGoogle();

    void signInWithFacebook(LoginButton btnFacebook);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    FirebaseUser isLoggedIn();

}
