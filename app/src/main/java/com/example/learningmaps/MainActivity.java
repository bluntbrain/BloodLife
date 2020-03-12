package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomNavigationView mBottomNavigationView;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private CardView buttontorequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttontorequest=findViewById(R.id.buttontorequestpage);
        buttontorequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, RequestBlood.class);
                startActivity(i);
            }
        });
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        /*
       mFusedLocationProviderClient=new FusedLocationProviderClient(MainActivity.this);
       mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
           @Override
           public void onComplete(@NonNull Task<Location> task) {
               if(task.isSuccessful())
               {
                   Location location=task.getResult();
               }
               else
               {
                   Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
               }
           }
       });

         */
        //LatLng user_location=new LatLng(location.getLatitude(),location.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(user_location));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_location,10));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        /*
        LatLng udupi =new LatLng(13.5,74.8);
        mMap.addMarker(new MarkerOptions().position(udupi));

        LatLng manglore=new LatLng(14,75);
        mMap.addMarker(new MarkerOptions().position(manglore));

        LatLng manglore1=new LatLng(14,76);
        mMap.addMarker(new MarkerOptions().position(manglore1));

        LatLng manglore2=new LatLng(15,76);
        mMap.addMarker(new MarkerOptions().position(manglore2));

        LatLng manglore3=new LatLng(14.5,73);
        mMap.addMarker(new MarkerOptions().position(manglore3));

        LatLng manglore4=new LatLng(13,75);
        mMap.addMarker(new MarkerOptions().position(manglore4));

        LatLng manglore5=new LatLng(10,75);
        mMap.addMarker(new MarkerOptions().position(manglore5));
        */
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:

                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(MainActivity.this,LeaderBoard.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(MainActivity.this,Profile.class);
                    startActivity(b);
                    break;

                case R.id.request_blood: Intent c=new Intent(MainActivity.this,Request.class);
                    startActivity(c);

                    break;

            }


            return false;
        }
    };

}
