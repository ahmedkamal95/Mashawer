package com.fekratoday.mashawer.utilities;

import android.annotation.TargetApi;
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

    public NotificationHelper(Context base) {
        context = base;
        if (Build.VERSION.SDK_INT >= 26) {
            createChannel();
        } else if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    @TargetApi(26)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
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
                .setTicker("Trip Alarm")
                .setAutoCancel(false)
                .setDefaults(5)
                .setContentInfo("Info")
                .setContentTitle("Trip on hold")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setContentText(trip.getName() + " waiting to start")
                .setPriority(2)
                .setContentIntent(PendingIntent.getActivity(context, trip.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build());
    }
}
