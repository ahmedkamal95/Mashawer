package com.fekratoday.mashawer.screens.loginscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fekratoday.mashawer.R;


public class MainLoginFragment extends Fragment {


    public MainLoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_login, container, false);
    }

}
