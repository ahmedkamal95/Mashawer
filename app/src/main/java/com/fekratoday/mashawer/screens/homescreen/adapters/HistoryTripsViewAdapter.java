package com.fekratoday.mashawer.screens.homescreen.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryTripsViewAdapter extends RecyclerView.Adapter<HistoryTripsViewAdapter.TripViewHolder> {
    private List<Trip> tripList;
    private Context context;

    public HistoryTripsViewAdapter(Context context, List<Trip> tripList) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trip_row_history_layout, viewGroup, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int position) {
        final Trip trip = tripList.get(position);

        tripViewHolder.titleView.setText(trip.getName());
        tripViewHolder.detailsView.setText("From: " + trip.getStartPoint() + ",  To: " + trip.getEndPoint());
        int day = trip.getDay();
        int mon = trip.getMonth();
        int year = trip.getYear();
        Calendar calender = Calendar.getInstance();
        calender.set(year, mon, day);
        tripViewHolder.timeView.setText(calender.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                ", " + day + " " + calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " + year + ", " + String.format("%02d", tripList.get(position).getHour()) + ":" +
                String.format("%02d", tripList.get(position).getMinute()));
        if (tripList.get(position).getNotesList().size() > 0) {
            for (Trip.Note note : tripList.get(position).getNotesList()) {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(note.getNoteBody());
                checkBox.setEnabled(false);
                if (note.getDoneState()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                tripViewHolder.noteView.addView(checkBox);
            }
        } else {
            TextView textView = new TextView(context);
            textView.setText("Empty Notes");
            tripViewHolder.noteView.addView(textView);
        }


        tripViewHolder.itemView.setOnClickListener(v -> {
            if ((tripViewHolder.itemView.findViewById(R.id.groupHistory).getVisibility()) == View.GONE) {
                tripViewHolder.itemView.findViewById(R.id.groupHistory).setVisibility(View.VISIBLE);
                tripViewHolder.myBtn.setImageResource(R.drawable.round_keyboard_arrow_up_24px);
            } else {
                tripViewHolder.itemView.findViewById(R.id.groupHistory).setVisibility(View.GONE);
                tripViewHolder.myBtn.setImageResource(R.drawable.round_keyboard_arrow_down_24px);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        TextView titleView, detailsView, timeView;
        LinearLayout noteView;
        ImageButton myBtn;

        TripViewHolder(@NonNull final View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.tripNameHistory);
            detailsView = itemView.findViewById(R.id.tripLineHistory);
            timeView = itemView.findViewById(R.id.tripTimeHistory);
            noteView = itemView.findViewById(R.id.notesViewHistory);
            myBtn = itemView.findViewById(R.id.expandBtnHistory);
        }
    }
}
