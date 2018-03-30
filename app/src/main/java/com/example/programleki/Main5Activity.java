package com.example.programleki;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    TextView textView21;

    public void zamkniecie(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        textView21 = (TextView) findViewById(R.id.textView21);
        String bazadziens = "";
        SQLiteDatabase myAlarms = openOrCreateDatabase("myalarmy.db", MODE_PRIVATE, null);
        // ponizsza linia tylko przy pierwszym uruchomieniu
        myAlarms.execSQL("CREATE TABLE IF NOT EXISTS useralarms (bazaidalarmu INT, bazadzien INT, bazagodzina INT, bazaminuta INT, bazazestaw INT)");
        Cursor myCursor2 = myAlarms.rawQuery("select bazaidalarmu, bazadzien, bazagodzina, bazaminuta, bazazestaw from useralarms", null);
        while(myCursor2.moveToNext()) {
            int bazaidalarmu = myCursor2.getInt(0);
            int bazadzien = myCursor2.getInt(1);
            if (bazadzien == 1) {bazadziens = "Niedziela";}
            if (bazadzien == 7) {bazadziens = "Sobota";}
            if (bazadzien == 5) {bazadziens = "Czwartek";}
            if (bazadzien == 6) {bazadziens = "Piątek";}
            int bazagodzina = myCursor2.getInt(2);
            int bazaminuta = myCursor2.getInt(3);
            int bazazestaw = myCursor2.getInt(4);
            textView21.setText(textView21.getText()+"\n"+bazaidalarmu+" "+bazadziens+" "+bazagodzina+" "+bazaminuta+" "+bazazestaw);
        }
        myCursor2.close();
        myAlarms.close();
    }
}
