package com.example.tipcollector;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

@SuppressLint("Registered")
public class NotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private NotificationManagerCompat notificationManager;
    Button btnNotification;
    Button btnNotificationTEST;


    private NotificationHelper mNotificationHelper;
    String title = "TITLE";
    String message = "TITLmessage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        btnNotification = findViewById(R.id.btnNotification);
        btnNotificationTEST = findViewById(R.id.btnNotificationTEST);
        btnNotificationTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOnChannel1(title,message);
            }
        });


        mNotificationHelper = new NotificationHelper(this);

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"timePicker");
            }
        });
        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);


        startAlarm(c);
    }
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
    public void sendOnChannel1(String title,String message){

        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(title,message);
        mNotificationHelper.getManager().notify(1,nb.build());
    }

    public void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
