package com.example.programleki;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD_1 = "textFiled 1";
    private static final String PREFERENCES_TEXT_FIELD_2 = "textFiled 2";
    private static final String PREFERENCES_TEXT_FIELD_3 = "textFiled 3";
    private static final String PREFERENCES_TEXT_FIELD_4 = "textFiled 4";
    TextView textView9;
    TextView textView11;
    TextView textView13;
    TextView textView15;
    private SharedPreferences preferences;

    public void zamkniecie(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        preferences = getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE);
        textView9 =  (TextView) findViewById(R.id.textView9);
        textView11 =  (TextView) findViewById(R.id.textView11);
        textView13 =  (TextView) findViewById(R.id.textView13);
        textView15 =  (TextView) findViewById(R.id.textView15);

        restoreData();
    }
    private void restoreData() {
        Boolean FromPreferences = preferences.getBoolean(PREFERENCES_TEXT_FIELD_1, false);
        textView9.setText(Boolean.toString(FromPreferences));
        Integer FromPreferences2 = preferences.getInt(PREFERENCES_TEXT_FIELD_2, 1);
        textView11.setText(Integer.toString(FromPreferences2));
        Float FromPreferences3 = preferences.getFloat(PREFERENCES_TEXT_FIELD_3, 10);
        textView13.setText(Float.toString(FromPreferences3));
        String textFromPreferences4 = preferences.getString(PREFERENCES_TEXT_FIELD_4, "ang");
        textView15.setText(textFromPreferences4);
    }

}
