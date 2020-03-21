package com.example.learningmaps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class RequestsTabs extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appbarlayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_tabs);
        tablayout=findViewById(R.id.tablayout_id);
        viewpager=findViewById(R.id.viewpager_id);
        appbarlayout=findViewById(R.id.appbarid);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentOldRequest(),"Requested");
        adapter.AddFragment(new FragmentMakeRequests(),"Request Blood");
        adapter.AddFragment(new FragmentPostCamp(),"Post Camp");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

    }
}
