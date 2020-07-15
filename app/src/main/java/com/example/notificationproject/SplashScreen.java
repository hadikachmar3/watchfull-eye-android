package com.example.notificationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    ShimmerTextView textView1;
    ShimmerTextView textView2;
    ShimmerFrameLayout shimmerFrameLayout;
//TextView textView1;
//    TextView textView2;
    Animation top, bottom;
    Shimmer shimmer;
    private int SPLASH_SCREEN = 2000;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      //  imageView= findViewById(R.id.imageView);
        textView1= findViewById(R.id.textView);
        textView2= findViewById(R.id.textView2);
       // shimmerFrameLayout =findViewById(R.id.ShimmerFrameLayoutId);
//        top= AnimationUtils.loadAnimation(this, R.anim.top);
//        bottom= AnimationUtils.loadAnimation(this, R.anim.bottom);
//        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
//        imageView.setAnimation(top);
//        textView1.setAnimation(bottom);
//        textView2.setAnimation(bottom);
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
//            shimmer.start(shimmerFrameLayout);
            shimmer.start(textView1);
            shimmer.start(textView2);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent= new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_SCREEN);
        final int welcomeScreenDisplay = 1750;
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
/**
 * use while to get the splash time. Use sleep() to increase
 * the wait variable for every 100L.
 */
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);

                } finally {
/**
 * Called after splash times up. Do some action after splash
 * times up. Here we moved to another main activity class
 */
                    startActivity(new Intent(SplashScreen.this,
                            MainActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();
    }

}