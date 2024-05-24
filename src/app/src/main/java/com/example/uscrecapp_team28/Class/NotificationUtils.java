package com.example.uscrecapp_team28.Class;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContextWrapper;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.uscrecapp_team28.R;

public class NotificationUtils extends ContextWrapper {
    private NotificationManager _notificationManager;
    private Context _context;

    public NotificationUtils(Context base)
    {
        super(base);
        _context = base;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body)
    {
        return new NotificationCompat.Builder(this, "notification_channel")
                .setSmallIcon(R.drawable.noti_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    private void createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("notification_channel", "Timeline notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if(_notificationManager == null)
        {
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return _notificationManager;
    }

    public void setReminder(long timeInMillis, String time)
    {
        Intent _intent = new Intent(_context, ReminderBroadcast.class);
        _intent.putExtra("time", time);
        PendingIntent _pendingIntent = PendingIntent.getBroadcast(_context, 0, _intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        _alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, _pendingIntent);
    }

    public void cancelReminder(){
        Intent _intent = new Intent(_context, ReminderBroadcast.class);
        PendingIntent.getBroadcast(_context, 0, _intent, PendingIntent.FLAG_IMMUTABLE).cancel();
    }

}
