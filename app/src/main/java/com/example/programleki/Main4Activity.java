package com.example.programleki;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    TextView textView20;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    Button buttonzapiszustaw2;

    public void zamkniecie(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView20 = (TextView) findViewById(R.id.textView20);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText8 = (EditText) findViewById(R.id.editText8);
        buttonzapiszustaw2 = (Button) findViewById(R.id.button11);

        SQLiteDatabase myDBleki = openOrCreateDatabase("mylekizestaw.db", MODE_PRIVATE, null);
        // ponizsza linia tylko przy pierwszym uruchomieniu
        myDBleki.execSQL("CREATE TABLE IF NOT EXISTS userleki (zestaw INT, leki1 VARCHAR(200), leki2 VARCHAR(200), leki3 VARCHAR(200))");
        Cursor myCursor = myDBleki.rawQuery("select zestaw, leki1, leki2, leki3 from userleki", null);
        while(myCursor.moveToNext()) {
            int zestaw = myCursor.getInt(0);
            String leki1 = myCursor.getString(1);
            String leki2 = myCursor.getString(2);
            String leki3 = myCursor.getString(3);
            textView20.setText(textView20.getText()+"\n"+zestaw+" "+leki1+" "+leki2+" "+leki3);
        }
        myCursor.close();
        myDBleki.close();
        initButtonOnClick();
    }
    private void initButtonOnClick() {
        buttonzapiszustaw2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveDataleki();
                showToast("Data leki saved");
            }
        });
    }
    private void showToast(String msg) {Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();}
    private void saveDataleki() {
        SQLiteDatabase myDBleki = openOrCreateDatabase("mylekizestaw.db", MODE_PRIVATE, null);
        ContentValues row2 = new ContentValues();
        row2.put("zestaw", editText5.getText().toString());
        row2.put("leki1", editText6.getText().toString());
        row2.put("leki2",editText7.getText().toString());
        row2.put("leki3", editText8.getText().toString());
        myDBleki.insert("userleki", null, row2);

        textView20.setText("");
        Cursor myCursor = myDBleki.rawQuery("select zestaw, leki1, leki2, leki3 from userleki", null);
        while(myCursor.moveToNext()) {
            int zestaw = myCursor.getInt(0);
            String leki1 = myCursor.getString(1);
            String leki2 = myCursor.getString(2);
            String leki3 = myCursor.getString(3);
            textView20.setText(textView20.getText()+"\n"+zestaw+" "+leki1+" "+leki2+" "+leki3);
        }
        myCursor.close();
        myDBleki.close();
    }
}
