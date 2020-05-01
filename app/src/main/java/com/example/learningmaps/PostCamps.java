package com.example.learningmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PostCamps extends AppCompatActivity {

    private EditText organisation, camp, time,date,details, contact, address;
    private ImageView done,backpost;
    private TextView expect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_camps);

        organisation=findViewById(R.id.organisation);
        camp=findViewById(R.id.camp);
        backpost=findViewById(R.id.backpost);
        expect=findViewById(R.id.expect);
        time=findViewById(R.id.time);
        date=findViewById(R.id.date);
        details=findViewById(R.id.otherdetails);
        contact=findViewById(R.id.contact);
        address=findViewById(R.id.address);

        done=findViewById(R.id.campdone);

        backpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ppp=new Intent(PostCamps.this, MainActivity.class);
                startActivity(ppp);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (organisation.getText().toString()== null || camp.getText().toString() == null || time.getText().toString() == null || date.getText().toString() == null || details.getText().toString() == null || contact.getText().toString() == null || address.getText().toString() == null)
                {
                    Toast.makeText(PostCamps.this,"Fields Empty",Toast.LENGTH_LONG).show();
                } else {
                    String message = "name of organisation: " + organisation.getText() + "\n" + "name of camp: " + camp.getText() + "\n" + "time: " + time.getText() + "\n" +
                            "date: " + date.getText() + "\n" + "details: " + details.getText() + "\n" + "contact: " + contact.getText() + "\n" + "address: " + address.getText();
                    String subject = organisation.getText() + " - Camp Request";
                    String[] to = {"bloodlifemanipal@gmail.com"};
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setData(Uri.parse("mailto:"));
                    email.setType("text/plain");

                    email.putExtra(Intent.EXTRA_EMAIL, to);
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);

                    email.setType("message/rfc822");
                    try {
                        startActivity(Intent.createChooser(email, "Send mail"));
                        expect.setAlpha(1);
                        // finish();
                        //Log.i("Finished sending email...", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(PostCamps.this,
                                "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }


            }   //startActivity(Intent.createChooser(email, "Choose an Email client :"));

        });
    }



    public void rootlayouttap(View view)
    {
        try {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {

        }
    }
}
