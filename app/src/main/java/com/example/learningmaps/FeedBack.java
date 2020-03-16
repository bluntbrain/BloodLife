package com.example.learningmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FeedBack extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        mButton=findViewById(R.id.emailusbutton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "bloodlifemanipal@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "hwllo");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                startActivity(intent);
            }
        });
    }
}
