package com.example.uscrecapp_team28.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.NotificationUtils;
import com.example.uscrecapp_team28.Helper.BookingInformationAdapter;
import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.Helper.CustomBroadcastReceiver;
import com.example.uscrecapp_team28.MyApplication;
import com.example.uscrecapp_team28.Helper.NotificationService;
import com.example.uscrecapp_team28.Helper.PastBookingInformationAdapter;
import com.example.uscrecapp_team28.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookingInformationActivity extends AppCompatActivity {
    private Agent agent_curr;
    private RecyclerView mRecyclerView;
    private RecyclerView mHistoryRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public RecyclerView getmHistoryRecyclerView() {
        return mHistoryRecyclerView;
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

    public RecyclerView.Adapter getmHistoryAdapter() {
        return mHistoryAdapter;
    }

    private RecyclerView.Adapter mHistoryAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mHistoryLayoutManager;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_information);
        //TODO add this code to all pages oncreate
        this.agent_curr = ((MyApplication) this.getApplication()).getAgent();
        mServiceIntent = new Intent(this, NotificationService.class);
        mServiceIntent.putExtra("userId",agent_curr.getUnique_userid());
        mServiceIntent.putExtra("command",false);
        if (!isMyServiceRunning(NotificationService.class) && agent_curr.getNotification_on()) {
            System.out.println("booking information activity: start the notification service");
            ContextCompat.startForegroundService(this,mServiceIntent);
        }
        //TODO end
        mRecyclerView = findViewById(R.id.recyclerView);
        mHistoryRecyclerView = findViewById(R.id.HistoryrecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mHistoryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mHistoryLayoutManager = new LinearLayoutManager(this);
        //call agent to display reservation
        Agent agent_curr = ((MyApplication) this.getApplication()).getAgent();

        HashMap<String,ArrayList<BookingItem>> m = agent_curr.view_all_reservations();

        ArrayList<BookingItem> futureList = (ArrayList<BookingItem>) m.get("future");
        ArrayList<BookingItem> historyList = (ArrayList<BookingItem>) m.get("history");
        if(futureList.size() > 0 && agent_curr.getNotification_on()){
            Date date1 = new Date();
            long timemilli = date1.getTime();
            String time1 = futureList.get(0).getText2();
//            String time1 = "2022-04-22 16:53";
            time1 = time1+":00";
            try{
                Date date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time1);
                int min = agent_curr.getNotification_time();
                long timemilli2 = date2.getTime();
                if((timemilli2 - 1000 * 60 * min) < timemilli ){
                    if(futureList.size() > 1){
                        System.out.print("Notify the second reservation at: ");
                        System.out.println(futureList.get(1).getText2());
                        String time3 = futureList.get(1).getText2();
                        reminderNotification(time3, min);
                    }
                    else{
                        System.out.println("Less than the notification minutes set");
                        cancelNotification();
                    }
                }
                else{
                    System.out.print("Notify the first reservation at: ");
                    System.out.println(futureList.get(0).getText2());
                    reminderNotification(futureList.get(0).getText2(), agent_curr.getNotification_time());
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        else{
            System.out.println("No reservation");
            System.out.println("Cancel the notification");
            cancelNotification();
        }
        System.out.println("get list");
        System.out.println(futureList);
        System.out.println(historyList);
        //set up adapter
        mAdapter = new BookingInformationAdapter(futureList,agent_curr);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mHistoryAdapter = new PastBookingInformationAdapter(historyList);
        mHistoryRecyclerView.setLayoutManager(mHistoryLayoutManager);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        //if the recreational center people size < max capacity -1, notify other users
        //notify other by pushing the other user id into the firebase

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // this method relocate the two buttons (two gyms) relative to different screens
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        // small window
        View window = findViewById(R.id.recyclerView);
        View historyWindow = findViewById(R.id.HistoryrecyclerView);
        LinearLayout.LayoutParams window_param = (LinearLayout.LayoutParams) window.getLayoutParams();
        LinearLayout.LayoutParams historyWindow_param = (LinearLayout.LayoutParams) historyWindow.getLayoutParams();
        window_param.height = height/ 3;
        historyWindow_param.height= height/ 3;
        window.setLayoutParams(window_param);
        historyWindow.setLayoutParams(historyWindow_param);
    }
    public void onClickReturn(View view){
        startActivity(new Intent(this, MapActivity.class));
    }

    @Override
    protected void onDestroy() {
//        System.out.println("ondestroy in service");
        if (!isMyServiceRunning(NotificationService.class) && agent_curr.getNotification_on()) {
            CustomBroadcastReceiver.setBroadcastReceiverId(agent_curr.getUnique_userid());
            Intent broadcastIntent = new Intent(this, CustomBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
        }
//        System.out.println("destroy the mainactivity service");
        super.onDestroy();
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("booking informationa activity: find already running service");
                return true;
            }
        }
        return false;
    }
    public void reminderNotification(String time, int minutes)
    {

        NotificationUtils _notificationUtils = new NotificationUtils(this);
        Date date1;
        time = time+":00";
        try{
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);
            long millis = date1.getTime();
            long sixtySeconds = 1000 * minutes * 60;
            long _triggerReminder = millis - sixtySeconds - 20000;
            _notificationUtils.setReminder(_triggerReminder, time);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public void cancelNotification(){
        NotificationUtils _notificationUtils = new NotificationUtils(this);
        _notificationUtils.cancelReminder();
    }
}