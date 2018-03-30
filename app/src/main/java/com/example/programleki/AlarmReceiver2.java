package com.example.programleki;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by a on 2018-03-01.
 */
public class AlarmReceiver2 extends BroadcastReceiver {

    /* Receives scheduled Alarm intents */
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

}

