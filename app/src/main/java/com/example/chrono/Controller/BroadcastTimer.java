package com.example.chrono.Controller;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class BroadcastTimer extends BroadcastReceiver {
    private long Start_Time= 60000;
    public  String time_left_txt;
    public  String tache_id;
    public TextView text_time, objectif_txt;
    public CountDownTimer ctdTime;
    public  Boolean timeRun=false;
    public long time_left= Start_Time;
    private Button start,reset;
    private NotificationManager mNotificationManager;
    public MediaPlayer bip;
    MyDatabaseHelper mydata ;



    @Override
    public void onReceive(Context context, Intent intent) {

        mydata = new MyDatabaseHelper(context);
        mydata.addBook("brodcast_save", Long.valueOf(1),"la_date_prevu", "la_date");

        StartTimer(time_left, context);

        //Toast.makeText(context, "Background service", Toast.LENGTH_SHORT).show();

    }

    public  void StartTimer(long _time_left, Context context){

        time_left=_time_left;
        ctdTime = new CountDownTimer(time_left,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                time_left = millisUntilFinished;
                int minutes = (int) (time_left / 1000) / 60;
                int second = (int) (time_left / 1000) % 60;
                time_left_txt = String.format(Locale.getDefault(),"%02d:%02d",minutes,second);
                Toast.makeText(context, time_left_txt, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onFinish() {
                Ringtone ringtone = RingtoneManager.getRingtone(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                ringtone.play();
                timeRun = false;
            }
        }.start();

    }
}
