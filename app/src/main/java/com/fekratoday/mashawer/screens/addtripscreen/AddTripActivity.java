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
import com.fekratoday.mashawer.utilities.DatePickerFragment;
import com.fekratoday.mashawer.utilities.TimePickerFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private PlaceAutocompleteFragment startPlaceAutocompleteFragment, endPlaceAutocompleteFragment;
    private Calendar calendar;
    private TextView tripTime,tripDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        calendar = Calendar.getInstance();

        initPlaceSearch();

    }

    private void initPlaceSearch() {
        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_from);
        endPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_to);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(5).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        startPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(AddTripActivity.this, String.valueOf(place.getLatLng().longitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(AddTripActivity.this, String.valueOf(place.getLatLng().latitude), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(AddTripActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        endPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        endPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(AddTripActivity.this, String.valueOf(place.getLatLng().longitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(AddTripActivity.this, String.valueOf(place.getLatLng().latitude), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(AddTripActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        TextView time = findViewById(R.id.tripTimeField);
        time.setText(hourOfDay +":"+minute);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        TextView date = findViewById(R.id.tripDateField);
        date.setText(dayOfMonth +"/"+(month+1)+"/"+year);
    }

    public void addNote(View v){
        EditText noteView = findViewById(R.id.noteField);
        TextView noteAddedNote = new TextView(AddTripActivity.this);
        LinearLayout noteList=findViewById(R.id.noteList);
        noteAddedNote.setText(noteView.getText());
        noteList.addView(noteAddedNote);
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
