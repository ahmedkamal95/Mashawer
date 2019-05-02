package com.fekratoday.mashawer.utilities;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.fekratoday.mashawer.model.beans.Trip;
import com.txusballesteros.bubbles.BubblesManager;

public class MapHelper {

    private Context context;
    private Trip trip;
    private BubblesManager bubblesManager;
    private int MY_PERMISSION = 2006;

    public MapHelper(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
    }

    public void showMap() {
        Intent mapIntent = new Intent("android.intent.action.VIEW",
                Uri.parse("http://maps.google.com/maps?saddr=" + trip.getStartPointLatitude() + "," + trip.getStartPointLongitude()
                        + "&daddr=" + trip.getEndPointLatitude() + "," + trip.getEndPointLongitude()));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Not found maps application", Toast.LENGTH_SHORT).show();
        }
        if (!trip.getNotesList().isEmpty()) {
            showFloatingWidget(trip.getId());
        }
        ((Activity) context).finish();
    }

    private void showFloatingWidget(int tripId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
//            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, MY_PERMISSION);
//            }
        } else {
//            Intent intent = new Intent(context, Service.class);
//            intent.putExtra("tripId", tripId);
//            context.startService(intent);
            initializeView(tripId);
        }
    }

    private void initializeView(int tripId) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra("tripId", tripId);
        context.startService(intent);
        ((Activity) context).finish();

    }
}
