package com.fekratoday.mashawer.screens.loginscreen.fragments;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;

public class MainLoginFragmentPresenterImpl implements MainLoginFragmentContract.LoginPresenter {

    private MainLoginFragmentContract.LoginView view;
    private LoginServicesInterface services;

    MainLoginFragmentPresenterImpl(MainLoginFragmentContract.LoginView view) {
        this.view = view;
        services = LoginServices.getInstance(null, null);
    }

    @Override
    public void startActivityForResult(Intent data, int requestCode) {
        view.startActivityForResult(data, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        services.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        view = null;
        services = null;
    }

    @Override
    public void onButtonGoogleClick(MainLoginFragment mainLoginFragment) {
        services.signInWithGoogle(mainLoginFragment, this);
    }

    @Override
    public void onButtonFacebookClick(LoginButton btnFacebook) {
        services.signInWithFacebook(btnFacebook);
    }

}
