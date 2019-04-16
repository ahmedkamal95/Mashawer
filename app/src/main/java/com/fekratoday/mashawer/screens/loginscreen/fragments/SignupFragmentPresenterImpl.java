package com.fekratoday.mashawer.screens.loginscreen.fragments;

import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;

public class SignupFragmentPresenterImpl implements SignupFragmentContract.SignupPresenter {

    private SignupFragmentContract.SignupView view;
    private LoginServicesInterface services;

    SignupFragmentPresenterImpl(SignupFragmentContract.SignupView view) {
        this.view = view;
        services = LoginServices.getInstance(null, null);
    }

    @Override
    public void onDestroy() {
        view = null;
        services = null;
    }

    @Override
    public void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment) {
        services.createUserWithEmailAndPassword(email, password, userName);
    }

}
