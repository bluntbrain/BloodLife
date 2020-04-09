package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.Parse;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView signup;

    private FirebaseAuth mAuth;


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

        mAuth=FirebaseAuth.getInstance();

        signup=findViewById(R.id.signuptext2);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ParseUser.logOut();
                FirebaseAuth.getInstance().signOut();
                loginUser();
                /*
                if(username.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Fields Empty",Toast.LENGTH_LONG).show();
                }
                //else if(username.getTextSize()!=10){

                  //  Toast.makeText(LoginActivity.this,"Invalid Number",Toast.LENGTH_LONG).show();

               // }
                else{
                    final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();
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
                            progressDialog.dismiss();
                        }
                    });
                }

                 */
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

    public void rootlayouttap(View view)
    {
        try {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {

        }
    }

    private void loginUser(){

        String emailbtn=username.getText()+"";
        String passwordbtn= password.getText()+"";

        if(TextUtils.isEmpty(emailbtn) || TextUtils.isEmpty(passwordbtn))
        {
            Toast.makeText(LoginActivity.this,"Fields Empty",Toast.LENGTH_LONG).show();

        }
        else if(passwordbtn.length()<4 && passwordbtn.length()>10)
        {
            Toast.makeText(LoginActivity.this,"Password length should between 4 to 10",Toast.LENGTH_LONG).show();

        }else
        {
            ProgressDialog progressDialog =new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Signing In");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailbtn,passwordbtn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });

            progressDialog.dismiss();

        }


    }

    public void onBackPressed(){
        finishAffinity();
        finish();

    }
}
