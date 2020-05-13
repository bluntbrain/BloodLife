package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class HowToUseApp extends AppCompatActivity {

    private TextView b1,b2;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use_app);

        b1 = findViewById(R.id.textnext);
        b2 = findViewById(R.id.textnexttwo);
        viewPager = findViewById(R.id.viewPagertwo);
        ImageAdaptertwo adapter = new ImageAdaptertwo(this);
        viewPager.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);
                if (viewPager.getCurrentItem() == 1) {
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.VISIBLE);
                }

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ix = new Intent(getApplicationContext(), MainActivity.class);
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
