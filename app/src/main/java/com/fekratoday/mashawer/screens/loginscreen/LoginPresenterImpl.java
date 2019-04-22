package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;
import com.fekratoday.mashawer.screens.loginscreen.fragments.MainLoginFragment;
import com.fekratoday.mashawer.screens.loginscreen.fragments.SignupFragment;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginServicesInterface loginServices;

    LoginPresenterImpl(LoginContract.View view) {
        this.view = view;
        loginServices = LoginServices.getInstance(view, this);
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
    public void onDestroy() {
        view = null;
        loginServices = null;
    }
}
