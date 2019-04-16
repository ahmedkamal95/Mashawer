package com.fekratoday.mashawer.screens.homescreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripActivity;

public class UpcomingTripsFragment extends Fragment {
    private Button addBtn;
    public UpcomingTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView=inflater.inflate(R.layout.fragment_upcoming_trips, container, false);
        addBtn = myView.findViewById(R.id.AddBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivity(intent);
            }
        });
        return myView;

    }

}
