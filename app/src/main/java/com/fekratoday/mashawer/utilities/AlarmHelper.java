package com.fekratoday.mashawer.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class AlarmHelper {

    public static void setAlarm(Context context, int tripId, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("tripId", tripId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tripId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else {
            Toast.makeText(context, "Error Please Try Again", Toast.LENGTH_SHORT).show();
        }

    }

    public static void cancelAlarm(Context context, int tripId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("tripId", tripId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), tripId, intent, FLAG_CANCEL_CURRENT);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        } else {
            Toast.makeText(context, "Error Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
