package com.codates.plantie.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Explode;
import androidx.transition.Slide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import com.codates.plantie.R;

public class SplashScreen extends AppCompatActivity {
private int time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(login);
                finish();
            }
        }, time);

    }


}
