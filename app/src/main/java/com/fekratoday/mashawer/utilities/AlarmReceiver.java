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

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        PowerManager.WakeLock wl = null;

        if (!pm.isScreenOn()) {
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("TAG");
            kl.disableKeyguard();
            wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG:");
            wl.acquire();
        }

        Toast.makeText(context, "Alarm worked", Toast.LENGTH_LONG).show();
        wl.release();

        Intent intent1 = new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("tripId", intent.getIntExtra("tripId", 0));
        context.startActivity(intent1);
    }
}