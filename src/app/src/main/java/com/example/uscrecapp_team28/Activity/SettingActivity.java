package com.example.uscrecapp_team28.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.Class.NotificationUtils;
import com.example.uscrecapp_team28.Class.Reservation;
import com.example.uscrecapp_team28.Helper.CustomBroadcastReceiver;
import com.example.uscrecapp_team28.Helper.NotificationService;
import com.example.uscrecapp_team28.MyApplication;
import com.example.uscrecapp_team28.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {

    private Agent agent_curr;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.agent_curr = ((MyApplication) this.getApplication()).getAgent();
        mServiceIntent = new Intent(this, NotificationService.class);
        mServiceIntent.putExtra("userId",agent_curr.getUnique_userid());
        mServiceIntent.putExtra("command",false);
        if (!isMyServiceRunning(NotificationService.class) && agent_curr.getNotification_on()) {
            System.out.println("setting activity: start the notification service");
            ContextCompat.startForegroundService(this,mServiceIntent);
        }
        TextView message = (TextView) findViewById(R.id.notification_show);
        SwitchCompat switchMain = (SwitchCompat)findViewById(R.id.switchMAIN);
        if (agent_curr.getNotification_on()) {
            switchMain.setChecked(true);
            switchMain.setText("Notification ON ");
            message.setText("Currently, you will receive notification " + agent_curr.getNotification_time() + " minutes before your next reservation");
        } else {
            switchMain.setChecked(false);
            switchMain.setText("Notification OFF");
            message.setText("Currently, you will NOT receive notification for upcoming reservations");
            AppCompatButton confirm_btn = (AppCompatButton) findViewById(R.id.confirm_noti_change);
            confirm_btn.setVisibility(View.INVISIBLE);
            RelativeLayout tb = (RelativeLayout) findViewById(R.id.textblock);
            tb.setVisibility(View.INVISIBLE);
        }

        switchMain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AppCompatButton confirm_btn = (AppCompatButton) findViewById(R.id.confirm_noti_change);
                    confirm_btn.setVisibility(View.VISIBLE);
                    RelativeLayout tb = (RelativeLayout) findViewById(R.id.textblock);
                    tb.setVisibility(View.VISIBLE);
                    agent_curr.setNotification_on(true);
                    // update database
                    try {
                        Thread thread_device = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try  {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                                    Statement s = connection.createStatement();
                                    String update = String.format("UPDATE user SET notification_on='%s' WHERE device_id='%s';", "true", agent_curr.getDevice_id());
                                    int i = s.executeUpdate(update);
                                    System.out.println("Update Complete");
                                    connection.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread_device.start();
                        thread_device.join();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TextView message = (TextView) findViewById(R.id.notification_show);
                    message.setText("Currently, you will receive notification " + agent_curr.getNotification_time() + " minutes before your next reservation");
                    switchMain.setText("Notification ON ");  //To change the text near to switch
                    System.out.println("Notification ON ");
                    mServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
                    mServiceIntent.putExtra("userId",agent_curr.getUnique_userid());
                    mServiceIntent.putExtra("command",false);
                    if (!isMyServiceRunning(NotificationService.class)) {
                        System.out.println("setting: start the notification service");
                        ContextCompat.startForegroundService(getApplicationContext(),mServiceIntent);
                    }
                }
                else {
                    AppCompatButton confirm_btn = (AppCompatButton) findViewById(R.id.confirm_noti_change);
                    confirm_btn.setVisibility(View.INVISIBLE);
                    RelativeLayout tb = (RelativeLayout) findViewById(R.id.textblock);
                    tb.setVisibility(View.INVISIBLE);
                    agent_curr.setNotification_on(false);
                    // update database
                    try {
                        Thread thread_device = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try  {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                                    Statement s = connection.createStatement();
                                    String update = String.format("UPDATE user SET notification_on='%s' WHERE device_id='%s';", "false", agent_curr.getDevice_id());
                                    int i = s.executeUpdate(update);
                                    System.out.println("Update Complete");
                                    connection.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread_device.start();
                        thread_device.join();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TextView message = (TextView) findViewById(R.id.notification_show);
                    message.setText("Currently, you will NOT receive notification for upcoming reservations");
                    switchMain.setText("Notification OFF");  //To change the text near to switch
                    System.out.println("Notification OFF");
                    //stop the service
//                    agent_curr.logout();
//                    agent_curr.setUnique_userid("");
                    mServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
                    mServiceIntent.putExtra("userId","");
                    mServiceIntent.putExtra("command",true);
                    startService(mServiceIntent);
                    stopService(mServiceIntent);
                    System.out.println("setting: stop the foreground service");
                    Intent i = new Intent(SettingActivity.this, SettingActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        });
        Reservation resv = new Reservation(agent_curr.getUnique_userid());
        HashMap<String, ArrayList<BookingItem>> hm = resv.display_all_reservation_info();
        ArrayList<BookingItem> futureList = hm.get("future");
        if(futureList.size() > 0 && agent_curr.getNotification_on()){
            Date date1 = new Date();
            long timemilli = date1.getTime();
            String time1 = futureList.get(0).getText2();
//            String time1 = "2022-04-22 16:53";
            time1 = time1+":00";
            try{
                Date date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time1);
                int min = agent_curr.getNotification_time();
                System.out.print("The notification time of the user is: ");
                System.out.println(min);
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
            System.out.println("Cancel the notification, no reservation");
            cancelNotification();
        }

    }



    public void onClickSettingBack(View view) {
        // System.out.println("BACK TO MAP PAGE");
        if (!this.agent_curr.check_loggedin()) {
            Intent i = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
        Intent i = new Intent(SettingActivity.this, MapActivity.class);
        startActivity(i);
        finish();
    }

    public void onClickConfirmChange(View view) {
        // System.out.println("BACK TO MAP PAGE");
        if (!this.agent_curr.check_loggedin()) {
            Intent i = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
        EditText minuteView = (EditText)findViewById(R.id.textremindedit);
        try
        {
            Integer new_minute = Integer.parseInt(minuteView.getText().toString());
            TextView message = (TextView) findViewById(R.id.notification_show);
            message.setText("Currently, you will receive notification " + new_minute + " minutes before your next reservation");
            System.out.println("SUCCESS");
            agent_curr.setNotification_time(new_minute);
            // update database
            // update database
            try {
                Thread thread_device = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            Class.forName("com.mysql.jdbc.Driver");
                            String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                            Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                            Statement s = connection.createStatement();
                            String update = String.format("UPDATE user SET notification_time='%d' WHERE device_id='%s';", new_minute, agent_curr.getDevice_id());
                            int i = s.executeUpdate(update);
                            System.out.println("Update Complete");
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_device.start();
                thread_device.join();
                minuteView.getText().clear();
                findViewById(R.id.wrongwrong).setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
                minuteView.getText().clear();
            }
            System.out.println(agent_curr.getNotification_time());
            findViewById(R.id.wrongwrong).setVisibility(View.INVISIBLE);
        }
        catch (Exception e)
        {
            // handle the exception
            findViewById(R.id.wrongwrong).setVisibility(View.VISIBLE);
            minuteView.getText().clear();
            System.out.println("FAIL");
        }

    }

    @Override
    protected void onDestroy() {
        // System.out.println("ondestroy in service");
        //create if on
        if (!isMyServiceRunning(NotificationService.class) && agent_curr.getNotification_on()) {
            CustomBroadcastReceiver.setBroadcastReceiverId(agent_curr.getUnique_userid());
            Intent broadcastIntent = new Intent(this, CustomBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
             System.out.println("send broadcast");
        }

        super.onDestroy();
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("setting activity: find already running service");
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
            _notificationUtils.setReminder(_triggerReminder,time);
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