package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LeaderBoard extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:
                    Intent a=new Intent(LeaderBoard.this,MainActivity.class);
                    startActivity(a);
                    break;

                case R.id.leaderboard_icon:

                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(LeaderBoard.this,Profile.class);
                    startActivity(b);
                    break;

                case R.id.request_blood:
                    Intent c=new Intent(LeaderBoard.this,Request.class);
                    startActivity(c);
                    break;

            }


            return false;
        }
    };
}
