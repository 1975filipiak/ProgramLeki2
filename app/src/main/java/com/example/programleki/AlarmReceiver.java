package com.example.programleki;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by a on 2018-03-10.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static PowerManager.WakeLock mWakeLock;
    /* Receives scheduled Alarm intents */
    public void onReceive(Context context, Intent intent) {
        /*
        mWakeLock = ((PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        */
        Bundle bundle = intent.getExtras();
        int workidenty = bundle.getInt("identyalarmu");
        int workzestaw = bundle.getInt("idzestawu");
        String workmessage = bundle.getString("infomessage");
        Intent intentalarm2 = new Intent(context, Main7Activity.class);
        intentalarm2.putExtra("identyalarmu4",workidenty);
        intentalarm2.putExtra("identyzestawu4",workzestaw);
        intentalarm2.putExtra("infomessage4",workmessage);
        intentalarm2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentalarm2);
        //mWakeLock.release();
        // wlasciwy kod
        //context.startActivity(new Intent(context, Main7Activity.class));
      }
}
