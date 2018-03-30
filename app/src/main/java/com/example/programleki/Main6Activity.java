package com.example.programleki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class Main6Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private EditText words2 = null;
    private Button speakBtn = null;
    private Button speakBtn2 = null;
    private static final int REQ_TTS_STATUS_CHECK = 0;
    private static final String TAG = "TTS Demo";
    private TextToSpeech mTts;
    EditText egodzina;
    EditText eminuta;
    EditText edzien;
    EditText ezestaw;

    public void zamkniecie(View view) {
        finish();    }

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        edzien = (EditText) findViewById(R.id.wordsToSpeak);
        ezestaw = (EditText) findViewById(R.id.wordsToSpeak2);
        egodzina = (EditText) findViewById(R.id.editText11);
        eminuta = (EditText) findViewById(R.id.editText12);
        speakBtn = (Button) findViewById(R.id.speak);
        speakBtn2 = (Button) findViewById(R.id.speak2);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
        SQLiteDatabase myAlarms = openOrCreateDatabase("myalarmy.db", MODE_PRIVATE, null);
        // ponizsza linia tylko przy pierwszym uruchomieniu
        myAlarms.execSQL("CREATE TABLE IF NOT EXISTS useralarms (bazaidalarmu INT, bazadzien INT, bazagodzina INT, bazaminuta INT, bazazestaw INT)");
        initButtonsOnClick();
    }
    private void initButtonsOnClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.speak:
                        doAlarm();
                        break;
                    case R.id.speak2:{
                        int ustawdzien = Integer.parseInt(edzien.getText().toString());
                        int ustawgodzine = Integer.parseInt(egodzina.getText().toString());
                        int ustawminute = Integer.parseInt(eminuta.getText().toString());
                        int ustawzestaw = Integer.parseInt(ezestaw.getText().toString());
                        int identyalarmu05 = ustawgodzine+ustawminute;
                        int numerrequest05 = ustawgodzine+ustawminute+5;
                        String infomessage05 = "alarm_numer:"+String.valueOf(numerrequest05);
                        saveAlarmy(ustawdzien,identyalarmu05,ustawgodzine,ustawminute,ustawzestaw);
                        scheduleAlarm4(identyalarmu05,ustawzestaw,infomessage05,numerrequest05,ustawgodzine,ustawminute,ustawdzien);
                        break;}
                    default:
                        break;
                }
            }
        };
        speakBtn.setOnClickListener(listener);
        speakBtn2.setOnClickListener(listener);
    }
    private void scheduleAlarm4(int nidentyalarmu, int nizestawu, String ninfomessage, int nnowyRequestCode, int lokagodzina, int lokaminuta, int lokadzien) {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /* Create the PendingIntent that will launch the BroadcastReceiver */
        Intent intentalarm1 = new Intent(this, AlarmReceiver.class);
        intentalarm1.putExtra("identyalarmu",nidentyalarmu);
        intentalarm1.putExtra("idzestawu",nizestawu);
        intentalarm1.putExtra("infomessage",ninfomessage);
        // PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);
        PendingIntent pending = PendingIntent.getBroadcast(this, nnowyRequestCode, intentalarm1, 0);
        // Set the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, lokadzien);
        calendar.set(Calendar.HOUR_OF_DAY, lokagodzina);
        calendar.set(Calendar.MINUTE, lokaminuta);
        /* Schedule Alarm with and authorize to WakeUp the device during sleep */
        // manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + przesuniecie * 1000, pending);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
    }
    public void doAlarm() {
        int ustawdzien = Integer.parseInt(edzien.getText().toString());
        int ustawgodzine = Integer.parseInt(egodzina.getText().toString());
        int ustawminute = Integer.parseInt(eminuta.getText().toString());
        int ustawzestaw = Integer.parseInt(ezestaw.getText().toString());
        int identyalarmu05 = ustawgodzine+ustawminute;
        int numerrequest05 = ustawgodzine+ustawminute+5;
        String infomessage05 = "alarm_numer:"+String.valueOf(numerrequest05);
        scheduleAlarm4(identyalarmu05,ustawzestaw,infomessage05,numerrequest05,ustawgodzine,ustawminute,ustawdzien);
    }
    private void saveAlarmy(int wstawdzien, int wstawidentyfikator, int wstawgodzine, int wstawminute, int wstawzestaw) {
        SQLiteDatabase myAlarms = openOrCreateDatabase("myalarmy.db", MODE_PRIVATE, null);
        // ponizsza linia tylko przy pierwszym uruchomieniu
        myAlarms.execSQL("CREATE TABLE IF NOT EXISTS useralarms (bazaidalarmu INT, bazadzien INT, bazagodzina INT, bazaminuta INT, bazazestaw INT)");
        // SQLiteDatabase myAlarms = openOrCreateDatabase("myalarmy", MODE_PRIVATE, null);
        ContentValues row2 = new ContentValues();
        row2.put("bazaidalarmu", String.valueOf(wstawidentyfikator));
        row2.put("bazadzien", String.valueOf(wstawdzien));
        row2.put("bazagodzina",String.valueOf(wstawgodzine));
        row2.put("bazaminuta", String.valueOf(wstawminute));
        row2.put("bazazestaw", String.valueOf(wstawzestaw));
        myAlarms.insert("useralarms", null, row2);
        myAlarms.close();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_TTS_STATUS_CHECK) {
            switch (resultCode) {
                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                    mTts = new TextToSpeech(this, this);
                    break;

                case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
                    Intent installIntent = new Intent();
                    installIntent.setAction(
                            TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installIntent);
                    break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
                    break;
            }
        }
    }
    @Override
    public void onInit(int status) {    }
}
