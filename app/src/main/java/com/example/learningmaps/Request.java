package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Request extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
   // private RippleBackground mRippleBackground;
   // private ImageView mImageView;
    private DatabaseReference mReference;
    private ArrayList<AddingItemsRequests> data;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
       // mImageView=findViewById(R.id.requests_backbutton);
       // mRippleBackground=findViewById(R.id.ripple);
       // mRippleBackground.startRippleAnimation();
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        data=new ArrayList<>();

        addlistdata();

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);





    }

    public  void addlistdata( )
    {



        mReference= FirebaseDatabase.getInstance().getReference("BloodRequests");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String victim_type = snapshot.child("victim_bloodtype").getValue().toString();
                    String victim_name = snapshot.child("victim_name").getValue().toString();
                    String victim_place = snapshot.child("victim_hospital").getValue().toString();
                    String victim_phone =snapshot.child("phone").getValue().toString();
                    String victim_status =snapshot.child("victim_status").getValue().toString();
                    String victim_units = "20";
                    data.add(new AddingItemsRequests(victim_name,victim_type,victim_place,victim_phone,victim_status,victim_units));
                }
                mAdapter= new RequestsCustomAdapter(data);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Request.this,"Server Respond :"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
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
                    Intent c=new Intent(Request.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Request.this,BheroLoading.class);
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
