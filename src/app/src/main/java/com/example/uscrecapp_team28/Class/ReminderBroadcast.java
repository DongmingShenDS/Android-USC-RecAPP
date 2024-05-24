package com.example.uscrecapp_team28.Class;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String time1 = intent.getStringExtra("time");
        NotificationUtils _notificationUtils = new NotificationUtils(context);
        String text = "You will have a reservation at: " + time1 + "!";
        NotificationCompat.Builder _builder = _notificationUtils.setNotification("Reminder", text);
        _notificationUtils.getManager().notify(101, _builder.build());
    }
}
