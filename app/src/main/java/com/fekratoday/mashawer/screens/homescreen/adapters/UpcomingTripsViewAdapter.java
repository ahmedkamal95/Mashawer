package com.fekratoday.mashawer.screens.homescreen.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.fekratoday.mashawer.model.database.TripDaoFirebase;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripActivity;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.fekratoday.mashawer.utilities.MapHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpcomingTripsViewAdapter extends RecyclerView.Adapter<UpcomingTripsViewAdapter.TripViewHolder> {
    private List<Trip> tripList;
    private Context context;
    private TripDaoSQL tripDaoSQL;
    private TripDaoFirebase tripDaoFirebase;

    public UpcomingTripsViewAdapter(Context context, List<Trip> tripList) {
        this.tripList = tripList;
        this.context = context;
        tripDaoSQL = new TripDaoSQL(context);
        tripDaoFirebase = new TripDaoFirebase();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trip_row_layout, viewGroup, false);
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
                tripViewHolder.noteView.addView(checkBox);
            }
        } else {
            TextView textView = new TextView(context);
            textView.setText("Empty Notes");
            tripViewHolder.noteView.addView(textView);
        }


        tripViewHolder.itemView.setOnClickListener(v -> {
            if ((tripViewHolder.itemView.findViewById(R.id.group).getVisibility()) == View.GONE) {
                tripViewHolder.itemView.findViewById(R.id.group).setVisibility(View.VISIBLE);
                tripViewHolder.myBtn.setImageResource(R.drawable.round_keyboard_arrow_up_24px);
            } else {
                tripViewHolder.itemView.findViewById(R.id.group).setVisibility(View.GONE);
                tripViewHolder.myBtn.setImageResource(R.drawable.round_keyboard_arrow_down_24px);
            }
        });

        tripViewHolder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Do you want to delete trip?")
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setPositiveButton(R.string.delete, (dialog, whichButton) -> {
                        tripDaoSQL.deleteTrip(trip.getId());
                        if (CheckInternetConnection.getInstance(context).checkInternet()) {
                            tripDaoFirebase.deleteTrip(trip);
                        }
                        tripList.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(android.R.string.cancel, null).show();
        });

        tripViewHolder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddTripActivity.class);
            intent.putExtra("trip",trip);
            context.startActivity(intent);
        });

        tripViewHolder.btnStart.setOnClickListener(v -> {
            trip.setTripState(true);
            tripDaoSQL.updateTrip(trip);
            if (CheckInternetConnection.getInstance(context).checkInternet()) {
                tripDaoFirebase.updateTrip(trip);
            }
            AlarmHelper.cancelAlarm(context, trip.getId());
            new MapHelper(context, trip).showMap();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        TextView titleView, detailsView, timeView;
        LinearLayout noteView;
        ImageButton myBtn, btnDelete, btnEdit, btnStart;

        TripViewHolder(@NonNull final View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.tripName);
            detailsView = itemView.findViewById(R.id.tripLine);
            timeView = itemView.findViewById(R.id.tripTime);
            noteView = itemView.findViewById(R.id.notesView);
            myBtn = itemView.findViewById(R.id.expandBtn);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnStart = itemView.findViewById(R.id.btnStart);
        }
    }
}
