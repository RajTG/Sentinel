package com.example.android.sentinel;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button stop;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stop = (Button)findViewById(R.id.stop);
        start= (Button)findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(new Intent(getApplicationContext(), senservice.class));
            }
        });



        stop.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),senservice.class));
            }
        });


    }
}
