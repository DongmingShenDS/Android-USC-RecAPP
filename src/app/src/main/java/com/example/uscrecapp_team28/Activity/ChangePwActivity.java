package com.example.uscrecapp_team28.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Helper.CustomBroadcastReceiver;
import com.example.uscrecapp_team28.Helper.NotificationService;
import com.example.uscrecapp_team28.MyApplication;
import com.example.uscrecapp_team28.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ChangePwActivity extends AppCompatActivity {

    private Agent agent_curr;
    String photourl;
    Intent mServiceIntent;
    Boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        this.agent_curr = ((MyApplication) this.getApplication()).getAgent();
        mServiceIntent = new Intent(this, NotificationService.class);
        mServiceIntent.putExtra("userId",agent_curr.getUnique_userid());
        mServiceIntent.putExtra("command",false);
        if (!isMyServiceRunning(NotificationService.class) && agent_curr.getNotification_on()) {
            System.out.println("profile activity: start the notification service");
            ContextCompat.startForegroundService(this,mServiceIntent);
        }
    }

    public void onClickChangePWConfirm(View view) {
        if (!this.agent_curr.check_loggedin()) {
            Intent i = new Intent(ChangePwActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
        // change PW in DB
        EditText PW_1 = (EditText)findViewById(R.id.PW_first);
        EditText PW_2 = (EditText)findViewById(R.id.PW_second);
        String pw1 = PW_1.getText().toString();
        String pw2 = PW_2.getText().toString();
        if (pw1.equals(pw2) && !pw1.isEmpty()) {
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
                            String update = String.format("UPDATE user SET password='%s' WHERE device_id='%s';", pw1, agent_curr.getDevice_id());
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
        }
        else {
            findViewById(R.id.notmatch).setVisibility(View.VISIBLE);
            PW_1.getText().clear();
            PW_2.getText().clear();
            return;
        }

        // logout and stop the service
        this.agent_curr.logout();
        mServiceIntent = new Intent(this, NotificationService.class);
        mServiceIntent.putExtra("userId",agent_curr.getUnique_userid());
        mServiceIntent.putExtra("command",true);
        startService(mServiceIntent);
        stopService(mServiceIntent);
        stop = true;
        System.out.println("logout: stop the foreground service");
        if (!this.agent_curr.check_loggedin()) {
            Intent i = new Intent(ChangePwActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
    }

    public void onClickPWBack(View view) {
        // System.out.println("BACK TO MAP PAGE");
        if (!this.agent_curr.check_loggedin()) {
            Intent i = new Intent(ChangePwActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
        Intent i = new Intent(ChangePwActivity.this, ProfileActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
//        System.out.println("ondestroy in service");
        //if not running and not stopped
        if (!isMyServiceRunning(NotificationService.class) && !stop && agent_curr.getNotification_on()) {
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
                System.out.println("profile activity: find already running service");
                return true;
            }
        }
        return false;
    }
}