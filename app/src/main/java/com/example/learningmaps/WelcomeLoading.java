package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.parse.Parse;
import com.parse.ParseUser;

public class WelcomeLoading extends AppCompatActivity {

    private CircularProgressBar mCircularProgressBar;
    private int animationDuration,flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_loading);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("EpQmd1UQ4LCPsqG65sqHKSJ04Yk5V1e4VK631IKc")
                .clientKey("Vm0egZoggfRMZFVfXCaOkm3XfErzApy6BXqZjHUk")
                .server("https://parseapi.back4app.com")
                .build()
        );
        flag=0;
        animationDuration=1800;
        mCircularProgressBar=findViewById(R.id.cirle_bar);
        mCircularProgressBar.setProgressWithAnimation(100, animationDuration);

        new CountDownTimer(2000,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
               if(ParseUser.getCurrentUser()!=null)
               {
                   flag=1;
               }else
               {
                   flag=2;
               }

            }

            @Override
            public void onFinish() {
                if(flag==2) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }else if(flag==1){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else
                {
                    Toast.makeText(WelcomeLoading.this,"Server not responding currently",Toast.LENGTH_LONG).show();
                    finishAffinity();
                    finish();
                }
            }
        }.start();





    }
}
