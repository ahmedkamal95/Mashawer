package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.screens.loginscreen.fragments.MainLoginFragment;
import com.fekratoday.mashawer.screens.loginscreen.fragments.SignupFragment;

public interface LoginCommunicator {

    void replaceFragment(String fragmentTag);

    void onButtonLoginClick(String email, String password);

    void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment);

    void startActivityForResult(Intent data, int requestCode);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onButtonGoogleClick();

    void onButtonFacebookClick(LoginButton btnFacebook);

}
