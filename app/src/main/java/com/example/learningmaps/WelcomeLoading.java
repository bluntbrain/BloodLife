package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeLoading extends AppCompatActivity {

    private int animationDuration,flag;
    private FirebaseUser mFirebaseUser;
    private ImageView welcomeimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_loading);

        
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        flag=0;

        welcomeimg=findViewById(R.id.welcomeimg);
        YoYo.with(Techniques.BounceInUp).duration(700).repeat(0)
                .playOn(welcomeimg);


        new CountDownTimer(1100,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {






            }

            @Override
            public void onFinish() {

                if(mFirebaseUser!=null)
                {
                    flag=1;
                }else
                {
                    flag=2;
                }

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
