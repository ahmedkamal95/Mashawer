package com.fekratoday.mashawer.screens.homescreen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripActivity;
import com.fekratoday.mashawer.screens.homescreen.adapters.UpcomingTripsViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpcomingTripsFragment extends Fragment {


    private FloatingActionButton addBtn;
    private RecyclerView.Adapter tripsAdapter;
    private RecyclerView.LayoutManager manager;
    private RecyclerView tripsView;
    private List<Trip> tripList;
    private TripDaoSQL tripDaoSQL;

    public UpcomingTripsFragment() {
        tripList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView=inflater.inflate(R.layout.fragment_upcoming_trips, container, false);
        addBtn = myView.findViewById(R.id.AddBtn);
        tripsView = myView.findViewById(R.id.tripsUpcommingView);
        tripDaoSQL = new TripDaoSQL(getActivity());
        manager = new LinearLayoutManager(getActivity());
        tripList.addAll(tripDaoSQL.getAllUpcommingTrips());
        tripsView.setLayoutManager(manager);
        tripsAdapter = new UpcomingTripsViewAdapter(getActivity(),tripList);
        tripsView.setAdapter(tripsAdapter);
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
