package com.fekratoday.mashawer.screens.homescreen.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoContract;
import com.fekratoday.mashawer.model.database.TripDaoPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class TripsHistoryFragment extends Fragment {
    private RecyclerView.Adapter tripsAdapter;
    private RecyclerView.LayoutManager manager;
    private RecyclerView tripsView;
    private List<Trip> tripList;
    private TripDaoContract tripDaoContract;

    public TripsHistoryFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tripHistoryFragmentView = inflater.inflate(R.layout.fragment_trips_history, container, false);
        tripsView = tripHistoryFragmentView.findViewById(R.id.tripsView);
        tripDaoContract = new TripDaoPresenterImpl();
        tripList = tripDaoContract.getallTrips();
        manager = new LinearLayoutManager(getContext());
        tripsView.setLayoutManager(manager);
        tripsAdapter = new TripsViewAdapter(tripList);
        tripsView.setAdapter(tripsAdapter);
        return tripHistoryFragmentView;
    }

}
