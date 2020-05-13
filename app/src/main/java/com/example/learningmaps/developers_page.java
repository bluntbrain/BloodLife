package com.example.learningmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class developers_page extends AppCompatActivity {

    private ImageView lovish,sarthak,rakshit,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_page);
        lovish=findViewById(R.id.lovish);
        sarthak=findViewById(R.id.sarthak);
        rakshit=findViewById(R.id.rakshit);
        backbtn=findViewById(R.id.imageView6);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(developers_page.this,ProfileFinal.class);
                startActivity(intent);
            }
        });

        lovish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.linkedin.com/in/lovish-badlani-250a05151/"));
                startActivity(i);
            }
        });

        rakshit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.linkedin.com/in/rakshit-tewari-a398a0151/"));
                startActivity(i);

            }
        });

        sarthak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.linkedin.com/in/sarthak-mohapatra-5a55a1189/"));
                startActivity(i);

            }
        });
    }
}
