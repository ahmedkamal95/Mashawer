package com.fekratoday.mashawer.screens.addtripscreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.DatePickerFragment;
import com.fekratoday.mashawer.utilities.TimePickerFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTripActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private PlaceAutocompleteFragment fragStartPoint, fragEndPoint;
    private Calendar calendar;
    private TextView tripTime, tripDate;
    private EditText edtTripName;
    private String tripName, startPoint, endPoint;
    int hour = -1, minute = -1, day = -1, month = -1, year = -1;
    private double startPointLatitude, startPointLongitude, endPointLatitude, endPointLongitude;
    private List<Trip.Note> noteList;
    private Trip trip;
    private TripDaoSQL tripDaoSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        edtTripName = findViewById(R.id.edtTripName);
        noteList = new ArrayList<>();
        calendar = Calendar.getInstance();
        trip = new Trip();
        tripDaoSQL = new TripDaoSQL(this);

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            if (checkData()) {
                int tripId = tripDaoSQL.insertTrip(trip);
                if (tripId > -1) {
                    Toast.makeText(this, String.valueOf(tripId), Toast.LENGTH_SHORT).show();
                    AlarmHelper.setAlarm(this, tripId, calendar);
                    Toast.makeText(this, "Trip Added", Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });

        initPlaceSearch();

    }

    private void initPlaceSearch() {
        fragStartPoint = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragStartPoint);
        fragEndPoint = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragEndPoint);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(5).build();
        fragStartPoint.setFilter(autocompleteFilter);
        fragStartPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                startPoint = place.getName().toString();
                startPointLatitude = place.getLatLng().latitude;
                startPointLongitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(AddTripActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        fragEndPoint.setFilter(autocompleteFilter);
        fragEndPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                endPoint = place.getName().toString();
                endPointLatitude = place.getLatLng().latitude;
                endPointLongitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(AddTripActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkData() {
        boolean check = false;
        tripName = edtTripName.getText().toString().trim();
        if (tripName.equals("")) {
            edtTripName.setError("Please Enter Trip Name");
        } else if (startPoint == null || startPoint.equals("")) {
            Toast.makeText(this, "Please Enter Start Point", Toast.LENGTH_SHORT).show();
        } else if (endPoint == null || endPoint.equals("")) {
            Toast.makeText(this, "Please Enter End Point", Toast.LENGTH_SHORT).show();
        } else if (endPoint.equals(startPoint)) {
            Toast.makeText(this, "End Point and Start Point can't be the same", Toast.LENGTH_SHORT).show();
        } else if (hour == -1 || minute == -1) {
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show();
        } else if (day == -1 || month == -1 || year == -1) {
            Toast.makeText(this, "Please Enter Date", Toast.LENGTH_SHORT).show();
        } else if (calendar.before(Calendar.getInstance())) {
            Toast.makeText(this, "Please Enter Right Date", Toast.LENGTH_SHORT).show();
        } else {
            trip.setName(tripName);
            trip.setStartPoint(startPoint);
            trip.setStartPointLatitude(startPointLatitude);
            trip.setStartPointLongitude(startPointLongitude);
            trip.setEndPoint(endPoint);
            trip.setEndPointLatitude(endPointLatitude);
            trip.setEndPointLongitude(endPointLongitude);
            trip.setHour(hour);
            trip.setMinute(minute);
            trip.setDay(day);
            trip.setMonth(month);
            trip.setYear(year);
            trip.setTripState(false);
            if (!noteList.isEmpty()) {
                trip.setNotesList(noteList);
            }
            check = true;
        }
        return check;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        this.hour = hourOfDay;
        this.minute = minute;

        TextView time = findViewById(R.id.tripTimeField);
        time.setText(hourOfDay + ":" + minute);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.day = dayOfMonth;
        this.month = month;
        this.year = year;

        TextView date = findViewById(R.id.tripDateField);
        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    public void addNote(View v) {
        EditText edtNoteView = findViewById(R.id.noteField);
        TextView noteAddedNote = new TextView(AddTripActivity.this);
        LinearLayout noteListView = findViewById(R.id.noteList);
        String noteText = edtNoteView.getText().toString().trim();
        if (noteText.equals("")) {
            edtNoteView.setError("Please Enter Note");
        } else {
            noteAddedNote.setText(noteText);
            Trip.Note note = new Trip.Note();
            note.setNoteBody(noteText);
            noteList.add(note);
            noteListView.addView(noteAddedNote);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
