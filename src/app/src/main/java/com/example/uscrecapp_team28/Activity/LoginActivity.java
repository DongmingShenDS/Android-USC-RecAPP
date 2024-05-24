package com.example.uscrecapp_team28.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.MyApplication;
import com.example.uscrecapp_team28.R;

public class LoginActivity extends AppCompatActivity {

    private Agent agent_curr;
    Intent mServiceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.agent_curr = ((MyApplication) this.getApplication()).getAgent();
        EditText editText = (EditText) findViewById(R.id.password);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onLoginHelper();
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void onLoginHelper() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mWifi.isConnected()) {
            System.out.println("LOGIN ACTIVITY: WIFI IS NOT CONNECTED!!!!!!!");
            Intent i = new Intent(LoginActivity.this, WifiActivity.class);
            startActivity(i);
            return;
        }
        System.out.println("LOGIN ACTIVITY: WIFI CONNECTED!!!!!!!");
        EditText usernameView = (EditText)findViewById(R.id.username);
        EditText passwordView = (EditText)findViewById(R.id.password);
        String usernameStr = usernameView.getText().toString();
        String passwordStr = passwordView.getText().toString();
        this.agent_curr = ((MyApplication) this.getApplication()).getAgent();
        this.agent_curr.setUsername(usernameStr);
        this.agent_curr.setPassword(passwordStr);
//        System.out.println(usernameStr);
//        System.out.println(passwordStr);
        if (this.agent_curr.user_login()) {
            System.out.println("LOGIN SUCCESS");
            Intent i = new Intent(LoginActivity.this, MapActivity.class);
            startActivity(i);
            finish();
        }
        else {
            findViewById(R.id.wrong).setVisibility(View.VISIBLE);
            usernameView.getText().clear();
            passwordView.getText().clear();
            // System.out.println("WRONG USERNAME / PASSWORD");
        }
    }

    public void onLoginClick(View view) {
        onLoginHelper();
    }
}