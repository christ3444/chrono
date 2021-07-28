package com.example.chrono;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServiceTimer extends Service {

   public String time_txt;

    public ServiceTimer(String time_txt) {
        this.time_txt = time_txt;
    }

    public String getTime_txt() {
        return time_txt;
    }

    public void setTime_txt(String time_txt) {
        this.time_txt = time_txt;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
