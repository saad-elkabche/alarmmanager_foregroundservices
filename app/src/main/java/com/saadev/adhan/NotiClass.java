package com.saadev.adhan;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class NotiClass extends Application {
    public static final String CHANNEL_ID="chennel_id";
    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"AlarmChennel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel
            );
        }
    }
}
