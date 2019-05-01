package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.loginscreen.LoginCommunicator;
import com.google.android.gms.common.SignInButton;


public class MainLoginFragment extends Fragment {

    private LoginButton btnFacebook;
    private LoginCommunicator communicator;

    public MainLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_login, container, false);

        btnFacebook = view.findViewById(R.id.btnFacebook);
        Button btnGoogle = view.findViewById(R.id.btnGoogle);
        Button btnMainLoginPage = view.findViewById(R.id.btnMainLoginPage);
        Button btnSignupMainPage = view.findViewById(R.id.btnSignupMainPage);
        ImageView imgLogo = view.findViewById(R.id.imgLogo);

        communicator = (LoginCommunicator) getActivity();

        btnFacebook.setVisibility(View.GONE);
        imgLogo.setImageResource(R.drawable.ic_launcher);

        btnGoogle.setOnClickListener(v -> communicator.onButtonGoogleClick());

        btnFacebook.setOnClickListener(v -> {
            communicator.onButtonFacebookClick(btnFacebook);
        });

        btnMainLoginPage.setOnClickListener(v -> {
            communicator.replaceFragment("loginFragment");
        });

        btnSignupMainPage.setOnClickListener(v -> {
            communicator.replaceFragment("signupFragment");
        });

        return view;
    }

}
