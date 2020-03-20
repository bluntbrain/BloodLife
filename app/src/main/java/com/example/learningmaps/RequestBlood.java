package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class RequestBlood extends AppCompatActivity {
    private ParseGeoPoint userlocation;
    private ImageView i1,i2;
    private Button done;
    private String Status,BloodGroup,Gender;
    private EditText name, mobile, units, place;
    private TextView postbloodcamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("EpQmd1UQ4LCPsqG65sqHKSJ04Yk5V1e4VK631IKc")
                .clientKey("Vm0egZoggfRMZFVfXCaOkm3XfErzApy6BXqZjHUk")
                .server("https://parseapi.back4app.com")
                .build()
        );

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        userlocation = bundle.getParcelable("userlocation");
//SPINNER CODE
        postbloodcamp=findViewById(R.id.postbloodcamp);
        postbloodcamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post=new Intent(RequestBlood.this,PostCamps.class);
                startActivity(post);
            }
        });
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        place=findViewById(R.id.place);
        units=findViewById(R.id.units);
        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 =findViewById(R.id.spinner2);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A+");
        arrayList.add("A-");
        arrayList.add("B+");
        arrayList.add("B-");
        arrayList.add("O+");
        arrayList.add("O-");
        arrayList.add("AB+");
        arrayList.add("AB-");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Emergency");
        arrayList2.add("Critical");
        arrayList2.add("Normal");
        arrayList2.add("Blood Camp");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 BloodGroup = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Blood Group: " + BloodGroup,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Status = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Blood Group: " + BloodGroup,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


//IMAGE CODE
        i1=findViewById(R.id.maleimage);
        i2=findViewById(R.id.femaleimage);
        done=findViewById(R.id.button3);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1.setImageResource(R.drawable.malepink);
                i2.setImageResource(R.drawable.femalegrey);
                Gender="male";
                //Toast.makeText(getApplicationContext(),"MALE",Toast.LENGTH_LONG).show();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.setImageResource(R.drawable.femalepink);
                i1.setImageResource(R.drawable.malegrey);
                Gender="female";
                //Toast.makeText(getApplicationContext(),"FEMALE",Toast.LENGTH_LONG).show();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText()==null || place.getText()==null || mobile.getText()==null || Gender==null || Status==null || BloodGroup==null || units.getText()==null){
                    Toast.makeText(RequestBlood.this,"Fields Empty",Toast.LENGTH_LONG).show();
                }
                else {
                    ParseObject parseObject = new ParseObject("Requests");
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    parseObject.put("victimname", name.getText().toString());
                    parseObject.put("victimplace", place.getText().toString());
                    parseObject.put("victimunits", Integer.parseInt(units.getText().toString()));
                    parseObject.put("victimphone", mobile.getText().toString());
                    parseObject.put("victimgender", Gender);
                    parseObject.put("victimstatus", Status);
                    parseObject.put("victimlocation",userlocation);
                    parseObject.put("victimtype", BloodGroup);
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(RequestBlood.this, "Successful Request", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RequestBlood.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(RequestBlood.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });



    }
}