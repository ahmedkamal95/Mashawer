package com.fekratoday.mashawer.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.alarmscreen.AlarmActivity;

public class NotificationHelper {
    private static final String channelID = "channelID";
    private static final String channelName = "ChannelName";
    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= 26) {
            createChannel();
        } else if (notificationManager == null) {
            notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public NotificationManager getManager() {
        return notificationManager;
    }

    public void createNotification(Trip trip) {
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra("tripId", trip.getId());
        long[] pattern = {500, 500};
        notificationManager.notify(trip.getId(), new Builder(context, channelID)
                .setOngoing(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(false)
                .setContentTitle(trip.getName() + " on hold")
                .setContentText("Click to start " + trip.getName())
                .setStyle(new NotificationCompat.BigTextStyle())
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setContentIntent(PendingIntent.getActivity(context, trip.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build());
    }
}
