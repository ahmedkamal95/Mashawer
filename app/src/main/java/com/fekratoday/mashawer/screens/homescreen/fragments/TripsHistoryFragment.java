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
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.screens.homescreen.adapters.TripsViewAdapter;
import java.util.ArrayList;
import java.util.List;


public class TripsHistoryFragment extends Fragment {

    private RecyclerView.Adapter tripsAdapter;
    private RecyclerView.LayoutManager manager;
    private RecyclerView tripsView;
    private List<Trip> tripList;
    private TripsHistoryContract tripsHistoryContract;
    private TripDaoSQL tripDaoSQL;

    public TripsHistoryFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tripHistoryFragmentView = inflater.inflate(R.layout.fragment_trips_history, container, false);
        tripsView = tripHistoryFragmentView.findViewById(R.id.tripsView);
        tripDaoSQL = new TripDaoSQL(getActivity());
        tripList = tripDaoSQL.getAllTrips();
//        tripList.add(new Trip("Aswan","aswan aswan aswan"));
//        tripList.add(new Trip("Luxur","Luxur Luxur Luxur"));
//        tripList.add(new Trip("ITI","ITI ITI ITI"));
//        tripList.add(new Trip("Aurgada","Aurgada Aurgada Aurgada"));

//        tripsHistoryContract = new TripsHistoryPresenterImpl(getContext());
//        tripList = tripsHistoryContract.getallTripsFirebase();

        manager = new LinearLayoutManager(getContext());
        tripsView.setLayoutManager(manager);
        tripsAdapter = new TripsViewAdapter(getActivity(),tripList);
        tripsView.setAdapter(tripsAdapter);
        return tripHistoryFragmentView;
    }

}
