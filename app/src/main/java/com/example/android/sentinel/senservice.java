package com.example.android.sentinel;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Raj on 10/08/2018.
 */

public class senservice extends Service implements SensorEventListener {

    MediaPlayer mediaplayer;
    SensorManager sm;
    Sensor sensor;
    Notification barNotif;
    String songId;
    int id;
    int t=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this," onstart ",Toast.LENGTH_LONG).show();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        songId="song";
        id = getResources().getIdentifier(songId, "raw", getPackageName());
        //mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.sky);
        mediaplayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        //mediaplayer.start();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent bIntent = new Intent(senservice.this, MainActivity.class);
        PendingIntent pbIntent = PendingIntent.getActivity(senservice.this, 0 , bIntent, 0);
        NotificationCompat.Builder bBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Sentinel")
                        .setContentText("Click to Stop")
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setContentIntent(pbIntent);
        barNotif = bBuilder.build();
        this.startForeground(1, barNotif);


        //then you should return sticky
        return Service.START_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        sm.unregisterListener(this);
        mediaplayer.stop();
    }


    @Override
    public void onSensorChanged(final SensorEvent Event) {

        if (Event.values[0] == 0) {
            //final int t= (int) Event.values[0];
            //Toast.makeText(this, (int) Event.values[0], Toast.LENGTH_LONG).show();
            //Intent panel = new Intent(this, Panel.class);
            //startActivity(panel);
            t = 1;
        }
        if (Event.values[0] > 0 && t == 1) {
            mediaplayer.start();
            AudioManager manager = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);
            KeyguardManager myKM = (KeyguardManager) this.getSystemService(this.KEYGUARD_SERVICE);
            while(manager.isMusicActive())
            {
                if( ! myKM.isKeyguardLocked()) {
                    mediaplayer.stop();

                }
            }

            //mediaplayer.start();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

