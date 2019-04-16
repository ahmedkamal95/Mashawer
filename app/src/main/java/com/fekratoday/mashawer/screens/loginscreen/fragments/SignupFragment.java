package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fekratoday.mashawer.R;


public class SignupFragment extends Fragment implements SignupFragmentContract.SignupView {

    private EditText edtEmail, edtUserName, edtPassword, edtConfirmPassword;
    private String email, userName, password, confirmPassword;
    private Button btnCreateAccount;
    private SignupFragmentContract.SignupPresenter presenter;

    public SignupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        edtEmail = view.findViewById(R.id.email);
        edtUserName = view.findViewById(R.id.userName);
        edtPassword = view.findViewById(R.id.password);
        edtConfirmPassword = view.findViewById(R.id.confirmPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);

        presenter = new SignupFragmentPresenterImpl(this);

        btnCreateAccount.setOnClickListener((view1) -> {
            if (checkData()) {
                presenter.onButtonRegisterClick(email, password, userName, this);
            }
        });

        return view;

    }

    private boolean checkData() {
        boolean check = false;
        email = edtEmail.getText().toString().trim();
        userName = edtUserName.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (email.equals("")) {
            edtEmail.setError("Please Enter Your Email");
        } else if (userName.equals("")) {
            edtPassword.setError("Please Enter Your User Name");
        } else if (password.equals("")) {
            edtPassword.setError("Please Enter Your Password");
        } else if (confirmPassword.equals("") || !confirmPassword.equals(password)) {
            edtConfirmPassword.setError("Password Not Match");
        } else {
            check = true;
        }
        return check;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
