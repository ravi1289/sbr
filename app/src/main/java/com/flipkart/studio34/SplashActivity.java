package com.flipkart.studio34;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                Intent nextActivity = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(nextActivity);
            }

        }, 2000);
    }

}
