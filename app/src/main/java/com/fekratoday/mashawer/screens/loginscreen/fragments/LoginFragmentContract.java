package com.fekratoday.mashawer.screens.loginscreen.fragments;

public interface LoginFragmentContract {

    interface LoginView {
    }

    interface LoginPresenter {
        void onDestroy();

        void onButtonLoginClick(String email, String password);
    }

}
