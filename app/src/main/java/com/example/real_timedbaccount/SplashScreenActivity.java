package com.example.real_timedbaccount;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000;
    private TextView welcomeTextView;
    private String textToAnimate = "Welcome to the Bookshelf!";
    private int index = 0;
    private long delay = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        welcomeTextView = findViewById(R.id.welcomeTextView);

        animateText(welcomeTextView, textToAnimate, delay);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, SignUp.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT+1000);
    }

    private void animateText(TextView textView,String text, long delay)  {
        Handler handler = new Handler();
        handler.post(new Runnable(){
            public void run() {
                if (index < text.length()) {
                    textView.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, delay);
                }
            }
        });

    }
}