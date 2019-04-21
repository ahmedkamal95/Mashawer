package com.fekratoday.mashawer.utilities;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.widget.Toast;

import com.fekratoday.mashawer.screens.alarmscreen.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int tripId = intent.getIntExtra("tripId", 0);
        Intent outIntent = new Intent(context, AlarmActivity.class);
        outIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outIntent.putExtra("tripId", tripId);
        context.startActivity(outIntent);
    }
}