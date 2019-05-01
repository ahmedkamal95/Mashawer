package com.fekratoday.mashawer.screens.homescreen.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryTripsViewAdapter extends RecyclerView.Adapter<HistoryTripsViewAdapter.TripViewHolder> {
    private List<Trip> tripList;
    private Context context;
    private List<Integer> counter;

    public HistoryTripsViewAdapter(Context context, List<Trip> tripList) {
        this.tripList = tripList;
        this.context = context;
        counter = new ArrayList<>();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_trip_row, viewGroup, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int position) {
        final Trip trip = tripList.get(position);
        final List<Trip.Note> noteList = trip.getNotesList();

        for (int i = 0; i < tripList.size(); i++) {
            counter.add(0);
        }

        tripViewHolder.txtTripName.setText(trip.getName());
        tripViewHolder.txtTripFrom.setText("From: " + trip.getStartPoint());
        tripViewHolder.txtTripTo.setText("To: " + trip.getEndPoint());
        int day = trip.getDay();
        int mon = trip.getMonth();
        int year = trip.getYear();
        Calendar calender = Calendar.getInstance();
        calender.set(year, mon, day);
        tripViewHolder.txtTripTime.setText(calender.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                ", " + day + " " + calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " + year + ", " + String.format("%02d", trip.getHour()) + ":" +
                String.format("%02d", trip.getMinute()));

        if (noteList.size() > 0) {
            NotesAdapter notesAdapter = new NotesAdapter(context, noteList);
            tripViewHolder.recyclerNotes.setLayoutManager(new LinearLayoutManager(context));
            tripViewHolder.recyclerNotes.setAdapter(notesAdapter);
        } else {
            tripViewHolder.recyclerNotes.setVisibility(View.GONE);
        }

        tripViewHolder.parentCardView.setOnClickListener(v -> {
            if ((counter.get(position) % 2 == 0) && (tripViewHolder.frameRecyclerNotes.getVisibility() == View.GONE)) {
                tripViewHolder.divider.setVisibility(View.VISIBLE);
                tripViewHolder.frameRecyclerNotes.setVisibility(View.VISIBLE);
                tripViewHolder.imgBtnExpand.setImageResource(R.drawable.round_keyboard_arrow_up_24px);
                if ((noteList.size() > 0)) {
                    tripViewHolder.recyclerNotes.setVisibility(View.VISIBLE);
                    tripViewHolder.txtEmptyNotes.setVisibility(View.GONE);
                } else {
                    tripViewHolder.recyclerNotes.setVisibility(View.GONE);
                    tripViewHolder.txtEmptyNotes.setVisibility(View.VISIBLE);
                }
            } else {
                tripViewHolder.divider.setVisibility(View.GONE);
                tripViewHolder.frameRecyclerNotes.setVisibility(View.GONE);
                tripViewHolder.imgBtnExpand.setImageResource(R.drawable.round_keyboard_arrow_down_24px);
            }

            counter.set(position, counter.get(position) + 1);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        CardView parentCardView;
        ImageButton imgBtnExpand, imgBtnDelete, imgBtnEdit, imgBtnStart;
        TextView txtTripName, txtTripFrom, txtTripTo, txtTripTime, txtEmptyNotes;
        ImageView imgTrip;
        View divider;
        FrameLayout frameRecyclerNotes;
        RecyclerView recyclerNotes;

        TripViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentCardView = itemView.findViewById(R.id.parentCardView);
            imgBtnExpand = itemView.findViewById(R.id.imgBtnExpand);
            txtTripName = itemView.findViewById(R.id.txtTripName);
            txtTripFrom = itemView.findViewById(R.id.txtTripFrom);
            txtTripTo = itemView.findViewById(R.id.txtTripTo);
            txtTripTime = itemView.findViewById(R.id.txtTripTime);
            imgTrip = itemView.findViewById(R.id.imgTrip);
            divider = itemView.findViewById(R.id.divider);
            imgBtnDelete = itemView.findViewById(R.id.imgBtnDelete);
            imgBtnEdit = itemView.findViewById(R.id.imgBtnEdit);
            imgBtnStart = itemView.findViewById(R.id.imgBtnStart);
            frameRecyclerNotes = itemView.findViewById(R.id.frameRecyclerNotes);
            recyclerNotes = itemView.findViewById(R.id.recyclerNotes);
            txtEmptyNotes = itemView.findViewById(R.id.txtEmptyNotes);
        }
    }
}
