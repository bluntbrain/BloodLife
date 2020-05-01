package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private int flag;
    private RecyclerView mRecyclerView;
    private BHeroCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mReference;
    private ArrayList<AddingItemsBHero> data;
    private AnyChartView chart;
    private TextView connectBtn,livesSaved,shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        data=new ArrayList<>();
        chart=findViewById(R.id.chart);
        shareBtn=findViewById(R.id.share_app_button);
        mRecyclerView=findViewById(R.id.bherocontainer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        connectBtn=findViewById(R.id.connect_btn);
        livesSaved=findViewById(R.id.lives_saved);

        addheroes();

        addchartValues();



        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LeaderBoard.this,SearchUsers.class);
                startActivity(i);
            }
        });
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setSelectedItemId(R.id.leaderboard_icon);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BloodLife");
                String shareMessage= "Manipal's own blood donation platform is here, download it now and save lives in no time\n*Remember !Heroes come in all types and sizes*\n\n";
                shareMessage = shareMessage + "playstore link....";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });

    }

    private void addheroes(){


        mReference= FirebaseDatabase.getInstance().getReference("Donoted");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count=0;
                int size=0;
                for(DataSnapshot snapshotone : dataSnapshot.getChildren()){
                    size++;
                }
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    count++;
                    if (size - 10< count) {
                        if (count <= size) {
                            String name = snapshot.child("name").getValue().toString();
                            String dp = snapshot.child("imageURl").getValue().toString();
                            data.add(new AddingItemsBHero(name, dp));
                        }
                    }
                }

                livesSaved.setText(size+" Lives");
                Collections.reverse(data);
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

                    Intent b=new Intent(LeaderBoard.this,ProfileFinal.class);
                    startActivity(b);
                    break;

                case R.id.request_blood_icon:

                    Intent c=new Intent(LeaderBoard.this,Request.class);
                    startActivity(c);
                    break;

            }


            return false;
        }
    };

    public void addchartValues(){

        Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("bloodtype");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int aplus=0,aneg=0,bplus=0,bneg=0,oplus=0,oneg=0,abplus=0,abneg=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.child("bloodtype").getValue().toString().equals("A+")){
                        aplus++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("A-")){
                        aneg++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("B+")){
                        bplus++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("B-")){
                        bneg++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("O+")){
                        oplus++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("O-")){
                        oneg++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("AB+")){
                        abplus++;
                    }else if(snapshot.child("bloodtype").getValue().toString().equals("AB-")){
                        abneg++;
                    }
                }
                int[] nos={aplus,aneg,bplus,bneg,oplus,oneg,abplus,abneg};
                String[] types={"A+","A-","B+","B-","O+","O-","AB-","AB+"};

                List<DataEntry> dataEntries=new ArrayList<>();
                for(int i=0;i<types.length;i++){
                    dataEntries.add(new ValueDataEntry(types[i],nos[i]));

                }
                Pie pie= AnyChart.pie();
                pie.innerRadius("70%");
                pie.title("Registered Donors");
                pie.labels().format("{%x} {%value}");


                pie.data(dataEntries);
                chart.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onBackPressed(){
        finishAffinity();
        Intent i =new Intent(LeaderBoard.this,MainActivity.class);
        startActivity(i);

    }
}
