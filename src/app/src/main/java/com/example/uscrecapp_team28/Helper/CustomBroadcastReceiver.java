package com.example.uscrecapp_team28.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

public class CustomBroadcastReceiver extends BroadcastReceiver {
    public static String mUniqueUserId;
    public static void setBroadcastReceiverId(String unique_user_id){
        mUniqueUserId = unique_user_id;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // System.out.println("create new service");
        Intent mServiceIntent = new Intent(context, NotificationService.class);
        mServiceIntent.putExtra("userId",mUniqueUserId);
        mServiceIntent.putExtra("command",false);
        ContextCompat.startForegroundService(context,mServiceIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, NotificationService.class));
//        } else {
//            context.startService(new Intent(context, NotificationService.class));
//        }
    }
}
