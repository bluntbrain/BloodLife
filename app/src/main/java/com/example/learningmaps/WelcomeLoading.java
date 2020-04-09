package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.parse.Parse;

public class WelcomeLoading extends AppCompatActivity {

    private CircularProgressBar mCircularProgressBar;
    private int animationDuration,flag;
    private FirebaseUser mFirebaseUser;


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
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        flag=0;
        animationDuration=1800;
        mCircularProgressBar=findViewById(R.id.cirle_bar);
        mCircularProgressBar.setProgressWithAnimation(100, animationDuration);

        new CountDownTimer(2000,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                /*
               if(ParseUser.getCurrentUser()!=null)
               {
                   flag=1;
               }else
               {
                   flag=2;
               }


                 */
                if(mFirebaseUser!=null)
                {
                    flag=1;
                }else
                {
                    flag=2;
                }
                /*
                if(flag==2) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else if(flag==1){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else
                {
                    Toast.makeText(WelcomeLoading.this,"Server not responding currently",Toast.LENGTH_LONG).show();
                    finishAffinity();
                    finish();
                }

                 */


            }

            @Override
            public void onFinish() {
                if(flag==2) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                }else if(flag==1){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

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
