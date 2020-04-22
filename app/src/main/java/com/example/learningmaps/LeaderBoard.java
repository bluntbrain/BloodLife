package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        data=new ArrayList<>();
        chart=findViewById(R.id.chart);
        mRecyclerView=findViewById(R.id.bherocontainer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        addheroes();

        addchartValues();



        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
                    Intent b=new Intent(LeaderBoard.this,ProfileFinal.class);
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

    public void addchartValues(){

        String[] types={"A+","A-","B+","B-","O+","O-","AB-","AB+"};
        int[] nos={2,4,1,7,2,1,4,5};
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

    public void onBackPressed(){
        finishAffinity();
        Intent i =new Intent(LeaderBoard.this,MainActivity.class);
        startActivity(i);

    }
}
