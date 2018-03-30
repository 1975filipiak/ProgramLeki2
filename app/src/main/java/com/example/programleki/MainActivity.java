package com.example.programleki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int THRI_Activity_RESULT_CODE = 0;
    private static final int THRI_Activity_RESULT_CODE_2 = 0;
    private static final int THRI_Activity_RESULT_CODE_3 = 0;
    private static final int THRI_Activity_RESULT_CODE_4 = 0;
    private static final int THRI_Activity_RESULT_CODE_5 = 0;
    private static final int THRI_Activity_RESULT_CODE_6 = 0;
    private Button btnStartService;
    private Button btnStopService;
    //AlarmManager alarmMgr;
    //PendingIntent alarmIntent;

    public void zamkniecie(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartService = (Button) findViewById(R.id.button16);
        btnStopService = (Button) findViewById(R.id.button17);
        initButtonsOnClick();
        if(getIntent().hasExtra("EXIT")) { finish(); }
    }
    private void scheduleAlarm4(int identyalarmu, String infomessage, int nowyRequestCode, int przesuniecie) {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /* Create the PendingIntent that will launch the BroadcastReceiver */
        Intent intentalarm1 = new Intent(this, AlarmReceiver.class);
        intentalarm1.putExtra("identyalarmu",identyalarmu);
        intentalarm1.putExtra("infomessage",infomessage);
        // PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);
        PendingIntent pending = PendingIntent.getBroadcast(this, nowyRequestCode, intentalarm1, 0);
        // Set the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // calendar.set(Calendar.DAY_OF_WEEK,6);
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, przesuniecie);
        /* Schedule Alarm with and authorize to WakeUp the device during sleep */
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
    }
    private void cancelAlarm2() {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /* Create the PendingIntent that would have launched the BroadcastReceiver */
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);
        /* Cancel the alarm associated with that PendingIntent */
        manager.cancel(pending);
    }
    private void initButtonsOnClick() {
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button16:
                        startMyService();
                        break;
                    case R.id.button17:
                        stopMyService();
                        break;
                    default:
                        break;
                }
            }
        };
        btnStartService.setOnClickListener(listener);
        btnStopService.setOnClickListener(listener);
    }
    private void startMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }
    private void stopMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }
    public void przywolaj(View view) {
        Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
        startActivityForResult(intent1, THRI_Activity_RESULT_CODE);
    }
    public void przywolajtest(View view) {
        Intent intent2 = new Intent(MainActivity.this, Main3Activity.class);
        startActivityForResult(intent2, THRI_Activity_RESULT_CODE_2);
    }
    public void przywolaj2(View view) {
        Intent intent3 = new Intent(MainActivity.this, Main4Activity.class);
        startActivityForResult(intent3, THRI_Activity_RESULT_CODE_3);
    }
    public void przywolajtest2(View view) {
        Intent intent4 = new Intent(MainActivity.this, Main5Activity.class);
        startActivityForResult(intent4, THRI_Activity_RESULT_CODE_4);
    }
    public void test1(View view) {
        Intent intent5 = new Intent(MainActivity.this, Main6Activity.class);
        startActivityForResult(intent5, THRI_Activity_RESULT_CODE_5);
    }
    public void test2(View view) {
        Intent intent6 = new Intent(MainActivity.this, Main7Activity.class);
        startActivityForResult(intent6, THRI_Activity_RESULT_CODE_6);
    }
}
