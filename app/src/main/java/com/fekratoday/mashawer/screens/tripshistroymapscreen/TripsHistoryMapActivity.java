package com.fekratoday.mashawer.screens.tripshistroymapscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;
import com.fekratoday.mashawer.utilities.mapdirectionhelpers.FetchURL;
import com.fekratoday.mashawer.utilities.mapdirectionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


public class TripsHistoryMapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private Polyline currentPolyline;
    private TripDaoSQL tripDaoSQL;
    private LatLng startPoint, endPoint;
    private float[] markerColor = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_YELLOW, BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_AZURE,
            BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_VIOLET,
            BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_ROSE};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_history_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");

        tripDaoSQL = new TripDaoSQL(this);
        List<Trip> tripList = tripDaoSQL.getAllHistoryTrips();
        int index = 0;
        for (Trip trip : tripList) {
            startPoint = new LatLng(trip.getStartPointLatitude(), trip.getStartPointLongitude());
            endPoint = new LatLng(trip.getEndPointLatitude(), trip.getEndPointLongitude());
            if (index > markerColor.length) {
                index = 0;
            }
            MarkerOptions startMarkerOptions = new MarkerOptions();
            startMarkerOptions.position(startPoint);
            startMarkerOptions.title("Start From Here");
            startMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor[index]));
            mMap.addMarker(startMarkerOptions);

            MarkerOptions endMarkerOptions = new MarkerOptions();
            endMarkerOptions.position(endPoint);
            endMarkerOptions.title("End Here");
            endMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor[index]));
            mMap.addMarker(endMarkerOptions);

            String url = getUrl(startPoint, endPoint);
            new FetchURL(TripsHistoryMapActivity.this).execute(url);

            index++;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10),5000, null);

    }

    private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + "driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Key
        String key = "AIzaSyCb7UX3N5kr8Nt7fNABcWLqwuRcbH72qyc";
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + key;
    }

    @Override
    public void onTaskDone(Object... values) {
        mMap.addPolyline((PolylineOptions) values[0]);
    }
}
