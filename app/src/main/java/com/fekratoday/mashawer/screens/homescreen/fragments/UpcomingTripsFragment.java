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
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripActivity;
import com.fekratoday.mashawer.screens.homescreen.FragmentCommunicator;
import com.fekratoday.mashawer.screens.homescreen.HomeCommunicator;
import com.fekratoday.mashawer.screens.homescreen.adapters.UpcomingTripsViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpcomingTripsFragment extends Fragment implements FragmentCommunicator {


    private FloatingActionButton btnAdd;
    private UpcomingTripsViewAdapter tripsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView tripsRecyclerView;
    private TextView txtNoTrips;
    private HomeCommunicator communicator;
    private List<Trip> tripList = new ArrayList<>();

    public UpcomingTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);

        btnAdd = view.findViewById(R.id.btnAdd);
        txtNoTrips = view.findViewById(R.id.txtNoTrips);
        tripsRecyclerView = view.findViewById(R.id.tripsUpcommingRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        tripsRecyclerView.setLayoutManager(layoutManager);
        communicator = (HomeCommunicator) getActivity();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTripActivity.class);
            startActivity(intent);
        });

        getUpcomingTripList();

        return view;
    }

    private void getUpcomingTripList() {
        tripList.clear();
//        tripList = communicator.getAllUpcommingTrips();
        tripsAdapter = new UpcomingTripsViewAdapter(this.getActivity(), tripList);
        tripsRecyclerView.setAdapter(tripsAdapter);

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
        tripList.addAll(communicator.getAllUpcommingTrips());
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
        communicator=null;
    }
}
