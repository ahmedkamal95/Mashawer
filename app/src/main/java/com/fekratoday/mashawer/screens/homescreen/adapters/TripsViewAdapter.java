package com.fekratoday.mashawer.screens.homescreen.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.homescreen.HomeActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TripsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Trip> tripList;
    private Activity currentActivity;
    public TripsViewAdapter(FragmentActivity activity, List<Trip> tripList) {
        this.tripList = tripList;
        currentActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trip_row_layout, viewGroup, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TextView titleView = viewHolder.itemView.findViewById(R.id.tripName);
        titleView.setText(tripList.get(i).getName());
        TextView detailsView = viewHolder.itemView.findViewById(R.id.tripLine);
        detailsView.setText("From: " + tripList.get(i).getStartPoint() + ",  To: " + tripList.get(i).getEndPoint());
        TextView timeView = viewHolder.itemView.findViewById(R.id.tripTime);
        int day = tripList.get(i).getDay();
        int mon = tripList.get(i).getMonth();
        int year = tripList.get(i).getYear();
        Calendar calender = Calendar.getInstance();
        calender.set(year, mon, day);
        timeView.setText(calender.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                ", " + day + " " + calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " + year + ", " + String.format("%02d", tripList.get(i).getHour()) + ":" +
                String.format("%02d", tripList.get(i).getMinute()));
        LinearLayout noteView = viewHolder.itemView.findViewById(R.id.notesView);
        if(tripList.get(i).getNotesList().size() > 0) {
            for (Trip.Note note : tripList.get(i).getNotesList()) {
                CheckBox checkBox = new CheckBox(currentActivity);
                checkBox.setText(note.getNoteBody());
                noteView.addView(checkBox);
            }
        }
        else{
            TextView textView = new TextView(currentActivity);
            textView.setText("Empty Notes");
            noteView.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    private class TripViewHolder extends RecyclerView.ViewHolder {
        private View tripView;

        TripViewHolder(@NonNull final View itemView) {
            super(itemView);
            tripView = itemView;
            final ImageButton myBtn = tripView.findViewById(R.id.expandBtn);
            myBtn.setOnClickListener(v -> {
                if ((itemView.findViewById(R.id.group).getVisibility()) == View.GONE) {
                    itemView.findViewById(R.id.group).setVisibility(View.VISIBLE);
                    myBtn.setImageResource(R.drawable.round_keyboard_arrow_up_24px);
                } else {
                    itemView.findViewById(R.id.group).setVisibility(View.GONE);
                    myBtn.setImageResource(R.drawable.round_keyboard_arrow_down_24px);
                }
            });
        }
    }
}
