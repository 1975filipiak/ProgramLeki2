package com.example.programleki;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.PowerManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main7Activity extends AppCompatActivity implements RecognitionListener, TextToSpeech.OnInitListener {
    EditText editText9;
    EditText editText10;
    Button recordvoice;
    TextView textView28;
    private SpeechRecognizer stt;
    private static final String TAG = "TTS Demo";
    int liczba_powtorzen;
    private static final int REQ_TTS_STATUS_CHECK = 1;
    private static final int STATUS_CHECK = 1;
    private TextToSpeech mTts;
    Timer timer;
    Timer timer2;
    Timer timer3;
    TimerTask timerTask;
    TimerTask timerTask2;
    TimerTask timerTask3;
    final Handler handler = new Handler();
    final Handler handler2 = new Handler();
    final Handler handler3 = new Handler();
    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getIntent().getExtras() == null)) {
            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            kl = km.newKeyguardLock("INFO");
            wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, "INFO");
            wl.acquire(); //wake up the screen
            kl.disableKeyguard();// dismiss the keyguard
        }
        setContentView(R.layout.activity_main7);
        stt = SpeechRecognizer.createSpeechRecognizer(this);
        stt.setRecognitionListener(this);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText10 = (EditText) findViewById(R.id.editText10);
        recordvoice = (Button) findViewById(R.id.button13);
        textView28 =  (TextView) findViewById(R.id.textView28);
        liczba_powtorzen = 1;
        TextView textviewnowy = (TextView) findViewById(R.id.textView27);
        String napis;
        if (!(getIntent().getExtras() == null)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
          Bundle bundle = getIntent().getExtras();
          int workidenty51 = bundle.getInt("identyalarmu4");
          int workzestawu51 = bundle.getInt("identyzestawu4");
          String workmessage51 = bundle.getString("infomessage4");
          napis = workmessage51+String.valueOf(workidenty51);
          textviewnowy.setText(napis);
          SQLiteDatabase myDBleki = openOrCreateDatabase("mylekizestaw.db", MODE_PRIVATE, null);
          // ponizsza linia tylko przy pierwszym uruchomieniu
          myDBleki.execSQL("CREATE TABLE IF NOT EXISTS userleki (zestaw INT, leki1 VARCHAR(200), leki2 VARCHAR(200), leki3 VARCHAR(200))");
          Cursor myCursor = myDBleki.rawQuery("select zestaw, leki1, leki2, leki3 from userleki", null);
          while(myCursor.moveToNext()) {
              int zestaw = myCursor.getInt(0);
              String leki1 = myCursor.getString(1);
              String leki2 = myCursor.getString(2);
              String leki3 = myCursor.getString(3);
              if (zestaw == workzestawu51){
              textviewnowy.setText(textviewnowy.getText()+"\n"+"zestaw nr: "+zestaw+"   "+leki1+" "+leki2+" "+leki3);}
          }
          myCursor.close();
          myDBleki.close();
        }
        initButtonOnClick();
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
    }
    /*
    private void turnScreenOn() {
        int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);}
    */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (!(getIntent().getExtras() == null)) {
          wl.release(); //when the activiy pauses, we should realse the wakelock
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!(getIntent().getExtras() == null)) {
            wl.acquire();//must call this!
        }
            if (liczba_powtorzen ==1) {
           startTimer();
           startTimer2();
           liczba_powtorzen=0;}
    }
    protected void onStop() {
        super.onStop();
    }
    public void startTimer() {
      timer = new Timer();
      initializeTimerTask();
      timer.schedule(timerTask, 3000);
    }
    public void startTimer2() {
        timer2 = new Timer();
        initializeTimerTask2();
        timer2.schedule(timerTask2, 6300);
    }
    public void startTimer3() {
        timer3 = new Timer();
        initializeTimerTask3();
        timer3.schedule(timerTask3, 2500);
    }
    public void stoptimertask(){
      if (timer != null) {
          timer.cancel();
          timer = null;
      }
    }
    public void stoptimertask2(){
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
    }
    public void stoptimertask3(){
        if (timer3 != null) {
            timer3.cancel();
            timer3 = null;
        }
    }
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      mTts.speak("Nadeszła pora na leki, potwierdz głosem pobranie leku", TextToSpeech.QUEUE_ADD, null);
                      stoptimertask();
                  }
              });
            }
        };
    }
    public void initializeTimerTask2() {
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                handler2.post(new Runnable() {
                    @Override
                    public void run() {
                        stoptimertask2();
                        recordvoice.callOnClick();
                    }
                });
            }
        };
    }
    public void initializeTimerTask3() {
        timerTask3 = new TimerTask() {
            @Override
            public void run() {
                handler3.post(new Runnable() {
                    @Override
                    public void run() {
                        stoptimertask3();
                        //finish();
                        stt.stopListening();
                        Intent fActivity = new Intent(getApplicationContext(), MainActivity.class);
                        fActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        fActivity.putExtra("EXIT", true);
                        startActivity(fActivity);
                        finish();
                    }
                });
            }
        };
    }
    private void initButtonOnClick() {
        recordvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
                Intent zamiar = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                zamiar.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getPackage().getName());
                zamiar.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.editText9));
                zamiar.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1500);
                zamiar.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1500);
                zamiar.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500);
// podpowiedź
// 1.LANGUAGE_MODEL_WEB_SEARCH : dla krótkich wyrażeń
// 2.LANGUAGE_MODEL_FREE_FORM : gdy nie wiadomo co będzie wyszukiwane
                //  zamiar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                zamiar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                zamiar.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 2);
                zamiar.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"pl-PL");
                startActivityForResult(zamiar, 0);
                stt.startListening(zamiar);
            ;}
        });}
    // Ponizszy fragment dotyczy obslugi glosowej
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int warunek=0;
        int warunek2=0;
        int warunek3=0;
        String wyraz1 ="";
        String wyraz2 ="";
        String wyraz3 ="";
        String wyraz5 ="";
        String wyraz6 ="";
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
        if (requestCode == 0){
            if (resultCode == RESULT_OK) {
                textView28.setText("");
                List<String> dopasowania = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (!dopasowania.isEmpty()) {
                    textView28.setText(dopasowania.get(0).toString());
                    editText9.setText(textView28.getText());
                    wyraz1 = editText9.getText().toString();
                    wyraz2 = "Potwierdzam";
                    wyraz3 = wyraz1.trim();
                    wyraz5 = "Przypomnij";
                    wyraz6 = "rezygnuję";
                    if (wyraz3.compareTo(wyraz2) == 0) { warunek = 1; } else { warunek = 6; }
                    if (wyraz3.compareTo(wyraz5) == 0) { warunek2 = 5; } else { warunek2 = 6; }
                    if (wyraz3.compareTo(wyraz6) == 0) { warunek3 = 5; } else { warunek3 = 6; }
                }
            } else {
                  mTts.speak("Nie zapisano żadnego polecenia", TextToSpeech.QUEUE_ADD, null);
                  super.onActivityResult(requestCode, resultCode, data);
                  stt.stopListening();
              }
             if (warunek == 1) { mTts.speak("Potwierdzono pobranie leku oraz zapisano dane", TextToSpeech.QUEUE_ADD, null);}
             if (warunek2 == 5) {mTts.speak("Przypomne póżniej o pobraniu leku", TextToSpeech.QUEUE_ADD, null);}
             if (warunek3 == 5) { mTts.speak("Rezygnacja z przyjęcia leków", TextToSpeech.QUEUE_ADD, null);}
             if ((warunek == 6) && (warunek2 == 6) && (warunek3 == 6)) { mTts.speak("Nieznane polecenie głosowe", TextToSpeech.QUEUE_ADD, null);}
            stt.stopListening();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onReadyForSpeech(Bundle params) {    }
    @Override
    public void onBeginningOfSpeech() {    }
    @Override
    public void onRmsChanged(float rmsdB) {    }
    @Override
    public void onBufferReceived(byte[] buffer) {    }
    @Override
    public void onEndOfSpeech() {    }
    @Override
    public void onError(int error) {    }
    @Override
    public void onResults(Bundle results) {    }
    @Override
    public void onPartialResults(Bundle partialResults) {    }
    @Override
    public void onEvent(int eventType, Bundle params) {    }
    public void zamkniecie(View view) {
        mTts.speak("Potwierdzono pobranie leków", TextToSpeech.QUEUE_ADD, null);
        startTimer3();
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void doSpeak(View view) {
        mTts.speak("Przypomne póżniej o pobraniu leku", TextToSpeech.QUEUE_ADD, null);
        startTimer3();
    }
    @Override
    public void onInit(int status) {
    }
    public void powrot(View view){
        finish();
    };
    public void Cancel(View view) {
        mTts.speak("Rezygnacja z przyjęcia leków", TextToSpeech.QUEUE_ADD, null);
        startTimer3();
    }
}
