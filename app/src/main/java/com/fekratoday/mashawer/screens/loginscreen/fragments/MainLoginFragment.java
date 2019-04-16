package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.R;
import com.google.android.gms.common.SignInButton;


public class MainLoginFragment extends Fragment implements MainLoginFragmentContract.LoginView {

    private SignInButton btnGoogle;
    private LoginButton btnFacebook;
    private MainLoginFragmentContract.LoginPresenter presenter;

    public MainLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_login, container, false);

        btnGoogle = view.findViewById(R.id.btnGoogle);
        btnFacebook = view.findViewById(R.id.btnFacebook);
        presenter = new MainLoginFragmentPresenterImpl(this);


        btnGoogle.setOnClickListener((view1) -> presenter.onButtonGoogleClick(this));

        btnFacebook.setOnClickListener((view1) -> {
            presenter.onButtonFacebookClick(btnFacebook);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
