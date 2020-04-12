package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class BheroLoading extends AppCompatActivity {

    private ImageView bherotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhero_loading);
        bherotext=findViewById(R.id.bherotext);

        new CountDownTimer(2800,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                bherotext.setAlpha(1f);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .playOn(bherotext);

            }
        }.start();


        new CountDownTimer(3600,5000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                    Intent i = new Intent(getApplicationContext(), LeaderBoard.class);
                    startActivity(i);

            }
        }.start();
    }
}
