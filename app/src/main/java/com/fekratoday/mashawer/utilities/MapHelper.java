package com.fekratoday.mashawer.utilities;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.alarmscreen.AlarmActivity;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;

import java.util.List;

public class MapHelper {
    
    private Context context;
    private Trip trip;
    private BubblesManager bubblesManager;
    private int MY_PERMISSION = 2006;
    
    public MapHelper(Context context, Trip trip){
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
        showFloatingWidget(trip.getNotesList());
        ((Activity)context).finish();
    }

    private void showFloatingWidget(List<Trip.Note> notesList) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                ((Activity)context).startActivityForResult(intent, MY_PERMISSION);
            }
        } else {
            Intent intent = new Intent(context, Service.class);
            context.startService(intent);
        }
        initializeBubblesManager();
        addNewBubble();
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(context)
                .setTrashLayout(R.layout.notification_trash_layout)
                .setInitializationCallback(this::addNewBubble)
                .build();
        bubblesManager.initialize();
    }

    private void addNewBubble() {
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(context).inflate(R.layout.notification_layout, null);
        bubbleView.setOnBubbleRemoveListener(bubble -> {
        });
        bubbleView.setOnBubbleClickListener(bubble -> {
            Toast.makeText(context, "Clicked !",
                    Toast.LENGTH_SHORT).show();
            String action;
//            Intent intent = new Intent(context, DynamicLayout.class);
//            context.startActivity(intent);
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }
}
