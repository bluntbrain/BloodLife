package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WelcomeLoading extends AppCompatActivity {

    private CircularProgressBar mCircularProgressBar;
    private int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_loading);
        animationDuration=3000;
        mCircularProgressBar=findViewById(R.id.cirle_bar);
        mCircularProgressBar.setProgressWithAnimation(100, animationDuration);

        new CountDownTimer(3500,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                //check if the person in logged in or notyoylo

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }.start();





    }
}
