package com.fekratoday.mashawer.screens.loginscreen;

import com.fekratoday.mashawer.model.networkservices.LoginServices;
import com.fekratoday.mashawer.model.networkservices.LoginServicesInterface;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginServicesInterface loginServices;

    public LoginPresenterImpl(LoginContract.View view) {
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
}
