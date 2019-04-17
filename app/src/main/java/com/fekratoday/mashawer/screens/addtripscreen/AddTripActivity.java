package com.fekratoday.mashawer.screens.addtripscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class AddTripActivity extends AppCompatActivity {

    PlaceAutocompleteFragment startPlaceAutocompleteFragment, endPlaceAutocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initPlaceSearch();

    }

    private void initPlaceSearch(){
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
}
