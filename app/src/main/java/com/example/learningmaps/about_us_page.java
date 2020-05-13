package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class about_us_page extends AppCompatActivity {

    private ImageView bacjbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);

        bacjbtn=findViewById(R.id.backfromaboutus);
        bacjbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(about_us_page.this,ProfileFinal.class);
                startActivity(intent);
            }
        });

    }
}
