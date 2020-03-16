package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class Profile extends AppCompatActivity {

    private ImageView rateus,feedback,TnC,logoutlogo;
    private TextView logouttext;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TnC=findViewById(R.id.tnc);
        logoutlogo=findViewById(R.id.logoutlogo);
        logouttext=findViewById(R.id.logouttext);
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

        TnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.websitepolicies.com/policies/view/iQcUErSr");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        logouttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent= new Intent(Profile.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        logoutlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(Profile.this);
                progressDialog.setMessage("Logging Out");
                progressDialog.show();
                ParseUser.logOut();
                progressDialog.dismiss();
                Intent intent= new Intent(Profile.this,LoginActivity.class);
                startActivity(intent);
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
