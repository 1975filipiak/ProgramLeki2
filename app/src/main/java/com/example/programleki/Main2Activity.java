package com.example.programleki;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private SharedPreferences preferences;
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD_1 = "textFiled 1";
    private static final String PREFERENCES_TEXT_FIELD_2 = "textFiled 2";
    private static final String PREFERENCES_TEXT_FIELD_3 = "textFiled 3";
    private static final String PREFERENCES_TEXT_FIELD_4 = "textFiled 4";
    Button buttonzapiszustaw;
    EditText n1EditText;
    EditText n2EditText;
    EditText n3EditText;
    EditText n4EditText;

    public void zamkniecie(View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        preferences = getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE);
        buttonzapiszustaw = (Button) findViewById(R.id.button5);
        n1EditText = (EditText) findViewById(R.id.editText1);
        n2EditText = (EditText) findViewById(R.id.editText2);
        n3EditText = (EditText) findViewById(R.id.editText3);
        n4EditText = (EditText) findViewById(R.id.editText4);
        initButtonOnClick();
        restoreData();
    }
    private void initButtonOnClick() {
        buttonzapiszustaw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveData();
                showToast("Data saved");
            }
        });
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void saveData() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Boolean editData1 = Boolean.parseBoolean(n1EditText.getText().toString());
        preferencesEditor.putBoolean(PREFERENCES_TEXT_FIELD_1, editData1);
        Integer editData2 = Integer.parseInt(n2EditText.getText().toString());
        preferencesEditor.putInt(PREFERENCES_TEXT_FIELD_2, editData2);
        Float editData3 = Float.parseFloat(n3EditText.getText().toString());
        preferencesEditor.putFloat(PREFERENCES_TEXT_FIELD_3, editData3);
        String editTextData4 = n4EditText.getText().toString();
        preferencesEditor.putString(PREFERENCES_TEXT_FIELD_4, editTextData4);
        preferencesEditor.commit();
    }
    private void restoreData() {
        Boolean FromPreferences = preferences.getBoolean(PREFERENCES_TEXT_FIELD_1, false);
        n1EditText.setText(Boolean.toString(FromPreferences));
        Integer FromPreferences2 = preferences.getInt(PREFERENCES_TEXT_FIELD_2, 1);
        n2EditText.setText(Integer.toString(FromPreferences2));
        Float FromPreferences3 = preferences.getFloat(PREFERENCES_TEXT_FIELD_3, 10);
        n3EditText.setText(Float.toString(FromPreferences3));
        String textFromPreferences4 = preferences.getString(PREFERENCES_TEXT_FIELD_4, "ang");
        n4EditText.setText(textFromPreferences4);
    }

}
