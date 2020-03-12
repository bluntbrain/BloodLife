package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("EpQmd1UQ4LCPsqG65sqHKSJ04Yk5V1e4VK631IKc")
                .clientKey("Vm0egZoggfRMZFVfXCaOkm3XfErzApy6BXqZjHUk")
                .server("https://parseapi.back4app.com")
                .build()
        );

        signup=findViewById(R.id.signuptext);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                if(username.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Fields Empty",Toast.LENGTH_LONG).show();
                }else {

                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e==null) {
                                // Hooray! The user is logged in.

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                // Signup failed. Look at the ParseException to see what happened.
                                //T.makeText(getApplicationContext(), "Wrong Credentials"+e.toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

    }
}
