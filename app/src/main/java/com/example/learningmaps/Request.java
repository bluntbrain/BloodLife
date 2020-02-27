package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Request extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

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
                    Intent c=new Intent(Request.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Request.this,LeaderBoard.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(Request.this,Profile.class);
                    startActivity(b);
                    break;

                case R.id.request_blood:

                    break;

            }


            return false;
        }
    };
}
