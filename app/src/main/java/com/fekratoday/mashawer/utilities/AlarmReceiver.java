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

        int id = intent.getIntExtra("tripId", 0);
        Intent intent1 = new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("tripId", id);
        Toast.makeText(context,id+"" , Toast.LENGTH_SHORT).show();
        context.startActivity(intent1);
    }
}