package com.fekratoday.mashawer.screens.homescreen.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.homescreen.FragmentCommunicator;
import com.fekratoday.mashawer.screens.homescreen.HomeCommunicator;
import com.fekratoday.mashawer.screens.homescreen.adapters.HistoryTripsViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class TripsHistoryFragment extends Fragment implements FragmentCommunicator {

    private HistoryTripsViewAdapter tripsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView tripsRecyclerView;
    private TextView txtNoTrips;
    private HomeCommunicator communicator;
    private List<Trip> tripList = new ArrayList<>();

    public TripsHistoryFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips_history, container, false);

        txtNoTrips = view.findViewById(R.id.txtNoTrips);
        tripsRecyclerView = view.findViewById(R.id.tripsHistoryRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        tripsRecyclerView.setLayoutManager(layoutManager);
        communicator = (HomeCommunicator) getActivity();

        getTripHistoryList();

        return view;
    }

    private void getTripHistoryList() {
        tripList.clear();
        tripList = communicator.getAllHistoryTrips();
        tripsAdapter = new HistoryTripsViewAdapter(this.getActivity(), tripList);
        tripsRecyclerView.setAdapter(tripsAdapter);

        if (tripList.isEmpty()) {
            txtNoTrips.setVisibility(View.VISIBLE);
        } else {
            txtNoTrips.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyChange();
    }

    @Override
    public void setNotifyChange() {
        notifyChange();
    }

    private void notifyChange(){
        tripList.clear();
        tripList.addAll(communicator.getAllHistoryTrips());
        tripsAdapter.notifyDataSetChanged();

        if (tripList.isEmpty()) {
            txtNoTrips.setVisibility(View.VISIBLE);
        } else {
            txtNoTrips.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        communicator = null;
    }
}
