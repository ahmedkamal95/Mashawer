package com.fekratoday.mashawer.screens.loginscreen;

import com.google.firebase.auth.FirebaseUser;

public interface LoginContract {

    interface View {
        void login(FirebaseUser user);

        void toast(String message);
    }

    interface Presenter {
        void toast(String message);

        void login(FirebaseUser currentUser);
    }
}
