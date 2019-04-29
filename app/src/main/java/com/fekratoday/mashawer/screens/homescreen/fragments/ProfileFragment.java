package com.fekratoday.mashawer.screens.homescreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.homescreen.HomeActivity;
import com.fekratoday.mashawer.screens.homescreen.HomeCommunicator;


public class ProfileFragment extends Fragment {

    private TextView txtName, txtEmail;
    private Button btnSync, btnLogout;
    private HomeCommunicator communicator;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);
        txtName = profileView.findViewById(R.id.txtName);
        txtEmail = profileView.findViewById(R.id.txtEmail);
        btnSync = profileView.findViewById(R.id.btnSync);
        btnLogout = profileView.findViewById(R.id.btnLogout);
        communicator = (HomeCommunicator) getActivity();

        txtName.setText(communicator.getUsername());
        txtEmail.setText(communicator.getEmail());

        btnSync.setOnClickListener(v -> communicator.sync());
        btnLogout.setOnClickListener(v -> communicator.logout());

        return profileView;
    }

}
