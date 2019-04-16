package com.fekratoday.mashawer.screens.loginscreen.fragments;

public interface SignupFragmentContract {

    interface SignupView {
    }

    interface SignupPresenter {
        void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment);

        void onDestroy();
    }

}
