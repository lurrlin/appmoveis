package com.ifsc.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor acs = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this,acs, SensorManager.SENSOR_DELAY_NORMAL);
        tv=findViewById(R.id.tv1);
        database = openOrCreateDatabase("bd", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS EVENTOS (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "value1 REAL(5,2), " +
                "value2 REAL(1,2), " +
                "value3 REAL(3,7))");
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        tv.setText(Float.toString(event.values[0])+ " : " +
            Float.toString(event.values[1])+ ": " +
            Float.toString(event.values[2]));

        ContentValues contentValues = new ContentValues();
        contentValues.put("value1", event.values[0]);
        contentValues.put("value1", event.values[1]);
        contentValues.put("value1", event.values[2]);
        Log.v("evento inserido", "inserido " + event);
        database.insert("eventos", null, contentValues);
    }
}