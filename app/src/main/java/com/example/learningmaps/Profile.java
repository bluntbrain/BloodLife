package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    private ImageView rateus,feedback;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        rateus=findViewById(R.id.rateuspic);
        feedback=findViewById(R.id.feedbackpic);


        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                View view= LayoutInflater.from(Profile.this).inflate(R.layout.activity_rate_us,null);
                builder.setView(view);
                builder.show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                View view= LayoutInflater.from(Profile.this).inflate(R.layout.activity_feed_back,null);
                builder.setView(view);
                builder.show();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:
                    Intent c=new Intent(Profile.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Profile.this,LeaderBoard.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:

                    break;

                case R.id.request_blood:
                    Intent b=new Intent(Profile.this,Request.class);
                    startActivity(b);

                    break;

            }


            return false;
        }
    };
}
