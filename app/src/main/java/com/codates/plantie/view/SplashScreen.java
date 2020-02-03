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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.codates.plantie.R;

public class SplashScreen extends AppCompatActivity {
private int time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView ivSplash = findViewById(R.id.img_logo);
        final TextView tvSplash = findViewById(R.id.tv_plantie);

        Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);

        ivSplash.startAnimation(anim);
        tvSplash.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent login = new Intent(SplashScreen.this,OnBoardingActivity.class);
                startActivity(login);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent login = new Intent(SplashScreen.this,LoginActivity.class);
//                startActivity(login);
//                finish();
//            }
//        }, time);

    }
}
