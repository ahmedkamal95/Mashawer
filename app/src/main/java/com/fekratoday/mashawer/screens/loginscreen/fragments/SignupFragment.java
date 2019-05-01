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


public class SignupFragment extends Fragment {

    private EditText edtEmailSignup, edtUserNameSignup, edtPasswordSignup, edtConfirmPasswordSignup;
    private String email, userName, password, confirmPassword;
    private Button btnCreateAccount, btnLoginSignupPage;
    private LoginCommunicator communicator;

    public SignupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        edtEmailSignup = view.findViewById(R.id.edtEmailSignup);
        edtUserNameSignup = view.findViewById(R.id.edtUserNameSignup);
        edtPasswordSignup = view.findViewById(R.id.edtPasswordSignup);
        edtConfirmPasswordSignup = view.findViewById(R.id.edtConfirmPasswordSignup);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);
        btnLoginSignupPage = view.findViewById(R.id.btnLoginSignupPage);

        communicator = (LoginCommunicator) getActivity();

        btnCreateAccount.setOnClickListener(v -> {
            if (checkData()) {
                communicator.onButtonRegisterClick(email, password, userName, this);
            }
        });

        btnLoginSignupPage.setOnClickListener(v -> communicator.replaceFragment("loginFragment"));

        return view;

    }

    private boolean checkData() {
        boolean check = false;
        email = edtEmailSignup.getText().toString().trim();
        userName = edtUserNameSignup.getText().toString().trim();
        password = edtPasswordSignup.getText().toString().trim();
        confirmPassword = edtConfirmPasswordSignup.getText().toString().trim();
        if (email.equals("")) {
            edtEmailSignup.setError("Please Enter Your Email");
        } else if (userName.equals("")) {
            edtPasswordSignup.setError("Please Enter Your User Name");
        } else if (password.equals("")) {
            edtPasswordSignup.setError("Please Enter Your Password");
        } else if (confirmPassword.equals("") || !confirmPassword.equals(password)) {
            edtConfirmPasswordSignup.setError("Password Not Match");
        } else {
            check = true;
        }
        return check;
    }

}
