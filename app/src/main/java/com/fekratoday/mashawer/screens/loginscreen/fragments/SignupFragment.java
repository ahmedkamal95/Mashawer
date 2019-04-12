package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fekratoday.mashawer.R;


public class SignupFragment extends Fragment {


    public SignupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Create Account");
        return inflater.inflate(R.layout.fragment_signup, container, false);

    }

}
