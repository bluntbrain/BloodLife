package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(13.345389, 74.796837);
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


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
