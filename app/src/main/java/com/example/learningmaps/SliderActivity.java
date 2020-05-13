package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SliderActivity extends AppCompatActivity {
private TextView b1,b2;
private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        b1=findViewById(R.id.getstart);
        b2=findViewById(R.id.getstart2);
        viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this,b1);
        viewPager.setAdapter(adapter);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);
                if(viewPager.getCurrentItem()==5){
                    b2.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.VISIBLE);
                }

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent ix=new Intent(getApplicationContext(),GettingLocationPerm.class);
        ix.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ix);
        finish();

    }
});

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
