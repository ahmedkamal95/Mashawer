package com.fekratoday.mashawer.screens.loginscreen.fragments;

import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;

public class LoginFragmentPresenterImpl implements LoginFragmentContract.LoginPresenter {

    private LoginFragmentContract.LoginView view;
    private LoginServicesInterface services;

    LoginFragmentPresenterImpl(LoginFragmentContract.LoginView view) {
        this.view = view;
        services = LoginServices.getInstance(null, null);
    }

    @Override
    public void onDestroy() {
        view = null;
        services = null;
    }

    @Override
    public void onButtonLoginClick(String email, String password) {
        services.signInWithEmailAndPassword(email, password);
    }

}
