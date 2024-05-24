package com.example.uscrecapp_team28.Helper;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.uscrecapp_team28.Activity.BookingInformationActivity;
import com.example.uscrecapp_team28.Activity.MainActivity;
import com.example.uscrecapp_team28.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    String unique_userid;
    Boolean stop;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    //create notification channel
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    "channel_2",
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        unique_userid = (String)intent.getExtras().get("userId");
        stop = (Boolean)intent.getExtras().get("command");
        // System.out.println("user id in foreground is "+ unique_userid);
        if(!stop){
            createNotificationChannels();
            startTimer();
        }else{
            stopForeground(true);
            stopSelfResult(startId);
        }
        return START_STICKY;
    }
    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                reference = db.collection("User").document("User");
                reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {
                            // System.out.println("current user id " + unique_userid);
                            // System.out.println("Current data: " + value.getData());
                            for (Map.Entry<String, Object> entry : value.getData().entrySet()){
                                //compare with current input
                                if(entry.getKey().equals(unique_userid)){
                                    //send notification
                                    // Create an Intent for the activity you want to start
                                    Intent resultIntent = new Intent(getApplicationContext(), BookingInformationActivity.class);
                                    // Create the TaskStackBuilder and add the intent, which inflates the back stack
                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                                    // Get the PendingIntent containing the entire back stack
                                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE);
                                    // System.out.println("send notification");
                                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),"channel_2").setSmallIcon(R.drawable.phoneicon).setContentTitle("USCRecAPP").setContentText("A spot available on "+entry.getValue()+"!").setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(resultPendingIntent);
                                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                                    notificationManagerCompat.notify(1,mBuilder.build());
                                    //delete the id from db
                                    Map<String,Object> delete_map = new HashMap<>();
                                    delete_map.put(unique_userid, FieldValue.delete());
                                    reference.update(delete_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // System.out.println("finish deleting");
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
            String NOTIFICATION_CHANNEL_ID = "channel_1";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.phoneicon)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_MAX)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
        stopForeground(true);
//        stopSelf();
        System.out.println("ondestroy in service");
//        if (!isMyServiceRunning(NotificationService.class)) {
//            CustomBroadcastReceiver.setBroadcastReceiverId(unique_userid);
//            Intent broadcastIntent = new Intent(this, CustomBroadcastReceiver.class);
//            sendBroadcast(broadcastIntent);
//
//        }
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        System.out.println("in myservice running " + serviceClass.getName());
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            System.out.println("activity manager: "+service.service.getClassName());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }
}
