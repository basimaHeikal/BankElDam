package com.ipda3.bankeldam.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManagerUser;

import static com.ipda3.bankeldam.helper.Constants.SPLASH_TIME;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = SPLASH_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if the user is already logged in ==>> directly start the start activity*/
        if (SharedPrefManagerUser.getInstance(this).isLoggedIn()) {
            finish();
            //opening start activity
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AfterSplashActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
