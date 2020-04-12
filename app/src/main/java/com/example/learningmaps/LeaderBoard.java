package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private KenBurnsView kbv ;
    private int flag;

    private RecyclerView mRecyclerView;
    private BHeroCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mReference;
    private ArrayList<AddingItemsBHero> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        kbv= findViewById(R.id.kenburns);

        data=new ArrayList<>();

        mRecyclerView=findViewById(R.id.bherocontainer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        addheroes();




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

    private void addheroes(){

        mReference= FirebaseDatabase.getInstance().getReference("Donoted");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = snapshot.child("name").getValue().toString();
                    String dp = snapshot.child("imageURl").getValue().toString();
                    data.add(new AddingItemsBHero(name,dp));
                }

                mAdapter= new BHeroCustomAdapter(data);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
