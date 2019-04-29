package com.fekratoday.mashawer.screens.addtripscreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.addtripscreen.adapters.AddNoteAdapter;
import com.fekratoday.mashawer.utilities.AlarmHelper;
import com.fekratoday.mashawer.utilities.CheckInternetConnection;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTripActivity extends AppCompatActivity {

    private PlaceAutocompleteFragment fragStartPoint, fragEndPoint;
    private Calendar calendar, calendarReturn;
    private TextView tripTime, tripDate, tripTimeReturn, tripDateReturn;
    private EditText edtTripName;
    private Button addTrip;
    private Switch switchTwoWay;
    private RecyclerView recyclerNoteList;
    private AddNoteAdapter addNoteAdapter;
    private EditText edtNoteView;
    private String tripName, startPoint, endPoint;
    private int hour = -1, minute = -1, day = -1, month = -1, year = -1;
    private int hourReturn = -1, minuteReturn = -1, dayReturn = -1, monthReturn = -1, yearReturn = -1;
    private double startPointLatitude, startPointLongitude, endPointLatitude, endPointLongitude;
    private List<Trip.Note> noteList;
    private Trip trip, tripReturn, tripEdit;
    private AddTripContract addTripContract;
    private boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        setupToolbar();
        initViews();
        initPlaceSearch();

        addTripContract = new AddTripPresenterImpl(this);

        noteList = new ArrayList<>();

        calendar = Calendar.getInstance();
        calendarReturn = Calendar.getInstance();
        trip = new Trip();
        trip.setOneWayTrip(true);
        tripReturn = new Trip();
        tripReturn.setOneWayTrip(true);

        tripEdit = (Trip) getIntent().getSerializableExtra("trip");
        if (tripEdit != null) {
            isEditable = true;
            setAdapter();
            setTripEdit();
        } else {
            isEditable = false;
            setAdapter();
        }

        switchTwoWay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tripTimeReturn.setVisibility(View.VISIBLE);
                tripDateReturn.setVisibility(View.VISIBLE);
                trip.setOneWayTrip(false);
            } else {
                tripTimeReturn.setVisibility(View.GONE);
                tripDateReturn.setVisibility(View.GONE);
                trip.setOneWayTrip(true);
            }
        });

        addTrip.setOnClickListener(v -> {
            if (checkData()) {
                if (isEditable) {
                    if (addTripContract.updateTripSQLite(trip)) {
                        AlarmHelper.setAlarm(this, trip.getId(), calendar);
                        Toast.makeText(this, "Trip Updated", Toast.LENGTH_SHORT).show();
                        if (CheckInternetConnection.getInstance(this).checkInternet()) {
                            addTripContract.addTripFirebase(trip);
                        }
                        finish();
                    } else {
                        Toast.makeText(this, "Error Trip Not Updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int tripId = addTripContract.addTripSQLite(trip);
                    if (tripId > -1) {
                        AlarmHelper.setAlarm(this, tripId, calendar);
                        Toast.makeText(this, "Trip Added", Toast.LENGTH_SHORT).show();
                        if (CheckInternetConnection.getInstance(this).checkInternet()) {
                            trip.setId(tripId);
                            addTripContract.addTripFirebase(trip);
                        }

                        if (!trip.isOneWayTrip()) {
                            int tripIdReturn = addTripContract.addTripSQLite(tripReturn);
                            if (tripIdReturn > -1) {
                                AlarmHelper.setAlarm(this, tripIdReturn, calendarReturn);
                                if (CheckInternetConnection.getInstance(this).checkInternet()) {
                                    tripReturn.setId(tripIdReturn);
                                    addTripContract.addTripFirebase(tripReturn);
                                }
                            }
                        }

                        finish();
                    } else {
                        Toast.makeText(this, "Error Trip Not Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    /**
     * Setting Toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.addTrip);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        edtTripName = findViewById(R.id.edtTripName);
        tripTime = findViewById(R.id.tripTimeField);
        tripDate = findViewById(R.id.tripDateField);
        tripTimeReturn = findViewById(R.id.tripTimeFieldReturn);
        tripDateReturn = findViewById(R.id.tripDateFieldReturn);
        switchTwoWay = findViewById(R.id.switchTwoWay);
        addTrip = findViewById(R.id.btnAdd);
        recyclerNoteList = findViewById(R.id.recyclerNoteList);
        edtNoteView = findViewById(R.id.noteField);
    }

    private void setAdapter(){
        addNoteAdapter = new AddNoteAdapter(this, noteList,addTripContract,isEditable);
        recyclerNoteList.setLayoutManager(new LinearLayoutManager(this));
        recyclerNoteList.setAdapter(addNoteAdapter);
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

            if (!trip.isOneWayTrip()) {
                if (hourReturn == -1 || minuteReturn == -1) {
                    Toast.makeText(this, "Please Enter Return Time", Toast.LENGTH_SHORT).show();
                } else if (dayReturn == -1 || monthReturn == -1 || yearReturn == -1) {
                    Toast.makeText(this, "Please Enter Return Date", Toast.LENGTH_SHORT).show();
                } else if (calendarReturn.before(calendar)) {
                    Toast.makeText(this, "Please Enter Right Return Date", Toast.LENGTH_SHORT).show();
                } else {
                    tripReturn.setName(tripName);
                    tripReturn.setStartPoint(endPoint);
                    tripReturn.setStartPointLatitude(endPointLatitude);
                    tripReturn.setStartPointLongitude(endPointLongitude);
                    tripReturn.setEndPoint(startPoint);
                    tripReturn.setEndPointLatitude(startPointLatitude);
                    tripReturn.setEndPointLongitude(startPointLongitude);
                    tripReturn.setHour(hourReturn);
                    tripReturn.setMinute(minuteReturn);
                    tripReturn.setDay(dayReturn);
                    tripReturn.setMonth(monthReturn);
                    tripReturn.setYear(yearReturn);
                    tripReturn.setTripState(false);
                    tripReturn.setOneWayTrip(true);
                    if (!noteList.isEmpty()) {
                        tripReturn.setNotesList(noteList);
                    }
                    setTrip();
                    check = true;
                }
            } else {
                setTrip();
                check = true;
            }
        }
        return check;
    }

    private void setTrip() {
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
    }

    private void setTripEdit() {
        edtTripName.setText(tripEdit.getName());
        fragStartPoint.setText(tripEdit.getStartPoint());
        fragEndPoint.setText(tripEdit.getEndPoint());
        tripTime.setText(tripEdit.getHour() + ":" + tripEdit.getMinute());
        tripDate.setText(tripEdit.getDay() + "/" + tripEdit.getMonth() + "/" + tripEdit.getYear());
        if (!tripEdit.getNotesList().isEmpty()) {
            noteList.addAll(tripEdit.getNotesList());
            addNoteAdapter.notifyDataSetChanged();
        }
        switchTwoWay.setVisibility(View.GONE);
        calendar.set(Calendar.YEAR, tripEdit.getYear());
        calendar.set(Calendar.MONTH, tripEdit.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, tripEdit.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, tripEdit.getHour());
        calendar.set(Calendar.MINUTE, tripEdit.getMinute());
        calendar.set(Calendar.SECOND, 0);

        tripName = tripEdit.getName();
        startPoint = tripEdit.getStartPoint();
        startPointLatitude = tripEdit.getStartPointLatitude();
        startPointLongitude = tripEdit.getStartPointLongitude();
        endPointLatitude = tripEdit.getEndPointLatitude();
        endPointLongitude = tripEdit.getEndPointLongitude();
        endPoint = tripEdit.getEndPoint();
        hour = tripEdit.getHour();
        minute = tripEdit.getMinute();
        day = tripEdit.getDay();
        month = tripEdit.getMonth();
        year = tripEdit.getYear();

        trip.setId(tripEdit.getId());
    }

    public void addNote(View v) {
        String noteText = edtNoteView.getText().toString().trim();
        if (noteText.equals("")) {
            edtNoteView.setError("Please Enter Note");
        } else {
            Trip.Note note = new Trip.Note();
            note.setNoteBody(noteText);
            noteList.add(note);
            addNoteAdapter.notifyDataSetChanged();
            edtNoteView.setText("");
            if (isEditable) {
                addTripContract.addNote(note, tripEdit.getId());
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog.OnDateSetListener listener = (view, year1, month1, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            day = dayOfMonth;
            month = month1;
            year = year1;

            TextView date = findViewById(R.id.tripDateField);
            date.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
        };
        new DatePickerDialog(AddTripActivity.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void showTimePickerDialog(View v) {
        TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute1) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute1);
            calendar.set(Calendar.SECOND, 0);

            hour = hourOfDay;
            minute = minute1;

            TextView time = findViewById(R.id.tripTimeField);
            time.setText(hourOfDay + ":" + minute1);
        };
        new TimePickerDialog(AddTripActivity.this, listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(AddTripActivity.this)).show();
    }

    public void showDatePickerDialogReturn(View view) {
        DatePickerDialog.OnDateSetListener listener = (view1, year, month, dayOfMonth) -> {
            calendarReturn.set(Calendar.YEAR, year);
            calendarReturn.set(Calendar.MONTH, month);
            calendarReturn.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dayReturn = dayOfMonth;
            monthReturn = month;
            yearReturn = year;

            tripDateReturn.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        };
        new DatePickerDialog(AddTripActivity.this, listener, calendarReturn.get(Calendar.YEAR), calendarReturn.get(Calendar.MONTH), calendarReturn.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showTimePickerDialogReturn(View view) {
        TimePickerDialog.OnTimeSetListener listener = (view1, hourOfDay, minute) -> {
            calendarReturn.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendarReturn.set(Calendar.MINUTE, minute);
            calendarReturn.set(Calendar.SECOND, 0);

            hourReturn = hourOfDay;
            minuteReturn = minute;

            tripTimeReturn.setText(hourOfDay + ":" + minute);
        };
        new TimePickerDialog(AddTripActivity.this, listener, calendarReturn.get(Calendar.HOUR_OF_DAY), calendarReturn.get(Calendar.MINUTE), DateFormat.is24HourFormat(AddTripActivity.this)).show();
    }

}
