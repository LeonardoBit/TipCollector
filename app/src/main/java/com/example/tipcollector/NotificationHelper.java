package com.example.tipcollector;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID= "channel1ID";
    public static final String channel1Name = "Channel 1";

    private NotificationManager mManager;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(){
         NotificationChannel channel1 = new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_HIGH);
         channel1.enableLights(true);
         channel1.enableVibration(true);
         channel1.setLightColor(R.color.colorPrimary);
         channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
         getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager(){
        if(mManager == null){
            mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
            return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title,String message){
        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);
    }


}

