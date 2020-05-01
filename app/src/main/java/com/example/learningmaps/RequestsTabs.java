package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class RequestsTabs extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appbarlayout;
    private ViewPager viewpager;
    private ImageView backbutton;
    private LatLng mycoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_tabs);
        tablayout=findViewById(R.id.tablayout_id);
        viewpager=findViewById(R.id.viewpager_id);
        appbarlayout=findViewById(R.id.appbarid);
        backbutton=findViewById(R.id.requesttab_backbutton);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentOldRequest(),"Your Requests");
        adapter.AddFragment(new FragmentMakeRequests(),"Request Blood");
        adapter.AddFragment(new FragmentPostCamp(),"Post camps" );

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        mycoordinates = bundle.getParcelable("userlocation");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RequestsTabs.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
