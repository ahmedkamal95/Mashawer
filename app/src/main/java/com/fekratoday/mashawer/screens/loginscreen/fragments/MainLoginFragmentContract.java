package com.fekratoday.mashawer.screens.loginscreen.fragments;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;

public interface MainLoginFragmentContract {

    interface LoginView {
        void startActivityForResult(Intent data, int requestCode);
    }

    interface LoginPresenter {
        void startActivityForResult(Intent data, int requestCode);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onDestroy();

        void onButtonGoogleClick(MainLoginFragment mainLoginFragment);

        void onButtonFacebookClick(LoginButton btnFacebook);
    }
}
