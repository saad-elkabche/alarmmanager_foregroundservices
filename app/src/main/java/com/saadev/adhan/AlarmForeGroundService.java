package com.saadev.adhan;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmForeGroundService extends Service {
    public static final String Action="STOP_ALARM";
  private static  MediaPlayer mediaPlayer;
    public static final String CancelAlarm="CancelAlarm";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.song2);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(123,getBuilder().build());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    if(!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    else{
                        mediaPlayer.reset();
                        mediaPlayer.start();
                    }

                }
                else{
                    mediaPlayer=MediaPlayer.create(AlarmForeGroundService.this,R.raw.song2);
                    AsyncTask.execute(this);
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
    private NotificationCompat.Builder getBuilder(){
        //for snooze Alarm
        Intent inten=new Intent(this,AlarmReciever.class);
        inten.setAction(Action);
        PendingIntent pendingAction=PendingIntent.getBroadcast(this,002,inten,PendingIntent.FLAG_IMMUTABLE);
        //for Cancel Alarm
        Intent intentCancel=new Intent(this,AlarmReciever.class);
        inten.setAction(CancelAlarm);
        PendingIntent pendingCancel=PendingIntent.getBroadcast(this,010,intentCancel,PendingIntent.FLAG_IMMUTABLE);
        //for openActivity
        Intent intentAct=new Intent(this,MainActivity.class);
        PendingIntent pendindingOpenActivity=PendingIntent.getActivity(this,000,intentAct,PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NotiClass.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_time)
                .setContentTitle("alarm")
                .setContentText("click Stop")
                .setContentIntent(pendindingOpenActivity)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_time,"Snooze",pendingAction)
                .addAction(R.drawable.ic_time,"Cancel",pendingCancel);

        return builder;
    }

    public static void StopAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public static void cancelAlaram(Context context){
        Intent intent=new Intent(context,AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,001,intent,PendingIntent.FLAG_IMMUTABLE);
        AlarmManager manager=(AlarmManager) context.getSystemService(ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }



}
