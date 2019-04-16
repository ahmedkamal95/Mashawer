package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fekratoday.mashawer.R;


public class LoginFragment extends Fragment implements LoginFragmentContract.LoginView {

    private EditText edtEmail, edtPassword;
    private String email, password;
    private Button btnLogin;
    private LoginFragmentContract.LoginPresenter presenter;


    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        edtEmail = view.findViewById(R.id.emailLogin);
        edtPassword = view.findViewById(R.id.passwordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);

        presenter = new LoginFragmentPresenterImpl(this);

        btnLogin.setOnClickListener((view1) -> {
            if (checkData()) {
                presenter.onButtonLoginClick(email, password);
            }
        });

        return view;
    }

    private boolean checkData() {
        boolean check = false;
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        if (email.equals("")) {
            edtEmail.setError("Please Enter Your Email");
        } else if (password.equals("")) {
            edtPassword.setError("Please Enter Your Password");
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
