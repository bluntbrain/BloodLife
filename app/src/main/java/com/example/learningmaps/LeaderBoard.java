package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LeaderBoard extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private KenBurnsView kbv ;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        kbv= findViewById(R.id.kenburns);

        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if(flag==0){
                    YoYo.with(Techniques.FadeOut)
                            .duration(1400)
                            .playOn(findViewById(R.id.dialoglay));
                    kbv.setImageResource(R.drawable.pictwo); flag++;
                YoYo.with(Techniques.FadeIn)
                        .duration(1400)
                        .playOn(findViewById(R.id.dialoglay));}
                else if(flag==1){
                    kbv.setImageResource(R.drawable.picthree); flag++;}
                else if(flag==2){
                    kbv.setImageResource(R.drawable.picone); flag++;}
                else if(flag==3){
                    kbv.setImageResource(R.drawable.pictwo); flag++;}
                else if(flag==4){
                        kbv.setImageResource(R.drawable.picthree); flag=0;}





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

    public void onBackPressed(){
        finishAffinity();
        Intent i =new Intent(LeaderBoard.this,MainActivity.class);
        startActivity(i);

    }
}
