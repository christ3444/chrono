package com.example.chrono;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class Timer extends AppCompatActivity {
    private long Start_Time; //= 600000;
    public  String time_left_txt;
    public TextView text_time, objectif_txt;
    public CountDownTimer ctdTime;
    public  Boolean timeRun=false;
    public long time_left;//= Start_Time;
    private Button start,reset;
    private NotificationManager mNotificationManager;
    public MediaPlayer bip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objectif_txt = findViewById(R.id.objectif);
        text_time = findViewById(R.id.text);
        start = findViewById(R.id.start);
        reset = findViewById(R.id.reset);

        bip=MediaPlayer.create(this,R.raw.bip);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("objectif")!= null)
        {
            objectif_txt.setText(bundle.getString("objectif"));
        }

        if(bundle.getString("time")!= null)
        {
              time_left=Long.valueOf(bundle.getString("time"))*3600000;
            //time_left=5000;
            Toast.makeText(Timer.this,bundle.getString("time"),Toast.LENGTH_LONG).show();
        }

        start.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               notif();
                if(timeRun){
                   pauseTimer();
                    text_time.setTextColor(getResources().getColor(R.color.red));
                }else {
                    startTimer();
                    text_time.setTextColor(getResources().getColor(R.color.white));
                   }
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pauseTimer();
                resetTimer();

                text_time.setTextColor(getResources().getColor(R.color.black));

            }
        });
            updatecountTxt();
    }
    public void startTimer(){
        ctdTime = new CountDownTimer(time_left,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished;
                updatecountTxt();

            }


            @Override
            public void onFinish() {
                timeRun = false;
                start.setText("Start");
                start.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
            }
        }.start();

        timeRun = true;
        start.setText("pause");
        reset.setVisibility(View.VISIBLE);
    }

    public void pauseTimer(){
        ctdTime.cancel();
        timeRun = false;
        start.setText("Start");
        reset.setVisibility(View.VISIBLE);
    }

    public void resetTimer(){
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("time")!= null)
        {
            time_left=Long.valueOf(bundle.getString("time"))*3600000;
            Toast.makeText(Timer.this,bundle.getString("time"),Toast.LENGTH_LONG).show();
        }

        updatecountTxt();
        //reset.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);

    }

    public void  updatecountTxt(){

        int heure = (int) (time_left / 1000)/3600;
        int minutes = (int) ((time_left / 1000) % 3600)/60;
        int second = (int) (((time_left / 1000) % 3600)) % 60;
        time_left_txt = String.format(Locale.getDefault(),"%02d:%02d:%02d",heure, minutes,second);
        text_time.setText(time_left_txt);


        if(heure==0 && minutes ==0 && second==0){
            bip.start();
        }
    }


    public void notif(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent_by_notif = new Intent(getApplicationContext().getApplicationContext(),Timer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent_by_notif, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        //bigText.bigText( "jojo");
        // bigText.setBigContentTitle("Today's Bible Verse");
        // bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_chrono);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("12h:34");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        // mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();

    }


}