package com.saadev.adhan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       Intent inten= new Intent(context,AlarmForeGroundService.class);
       if(intent.getAction()!=null){
           if(intent.getAction().equals(AlarmForeGroundService.Action)){
               AlarmForeGroundService.StopAlarm();
               context.stopService(inten);
               Log.d("hello",intent.getAction());
               return;
           }
           else if(intent.getAction().equals(AlarmForeGroundService.CancelAlarm)){
               AlarmForeGroundService.cancelAlaram(context);
               AlarmForeGroundService.StopAlarm();
               context.stopService(inten);
           }
           Log.d("hello",intent.getAction());
       }
         ContextCompat.startForegroundService(context,inten);
       Log.d("hello","stratForeGroundService");
    }
}