package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.loginscreen.LoginCommunicator;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private EditText edtEmailLogin, edtPasswordLogin;
    private String email, password;
    private Button btnLogin, btnSignupLoginPage;
    private Toolbar toolbar;
    private LoginCommunicator communicator;


    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        edtEmailLogin = view.findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = view.findViewById(R.id.edtPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignupLoginPage = view.findViewById(R.id.btnSignupLoginPage);
        toolbar = view.findViewById(R.id.toolbar);
        setupToolbar();

        communicator = (LoginCommunicator) getActivity();

        btnLogin.setOnClickListener(v -> {
            if (checkData()) {
                communicator.onButtonLoginClick(email, password);
            }
        });

        btnSignupLoginPage.setOnClickListener(v -> communicator.replaceFragment("signupFragment"));

        return view;
    }

    /**
     * Setting Toolbar
     */
    private void setupToolbar() {
        toolbar.setTitle(R.string.login);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
    }

    private boolean checkData() {
        boolean check = false;
        email = edtEmailLogin.getText().toString().trim();
        password = edtPasswordLogin.getText().toString().trim();
        if (email.equals("")) {
            edtEmailLogin.setError("Please Enter Your Email");
        } else if (password.equals("")) {
            edtPasswordLogin.setError("Please Enter Your Password");
        } else {
            check = true;
        }
        return check;
    }

}
