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

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView signup,clicktoforgot;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        signup=findViewById(R.id.signuptext2);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        clicktoforgot=findViewById(R.id.clicktoforget);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                loginUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

        clicktoforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgotPassword.class);
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

        String emailbtn=username.getText().toString();
        String passwordbtn= password.getText().toString();

        if(TextUtils.isEmpty(emailbtn) || TextUtils.isEmpty(passwordbtn))
        {
            Toast.makeText(LoginActivity.this,"Fields Empty",Toast.LENGTH_LONG).show();

        }
        else if(passwordbtn.length()<6 )
        {
            Toast.makeText(LoginActivity.this,"Password length should min of 6 characters",Toast.LENGTH_LONG).show();

        }else
        {
            final ProgressDialog progressDialog =new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Signing In");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailbtn,passwordbtn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        progressDialog.dismiss();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }


    }

    public void onBackPressed(){
        finishAffinity();
        finish();

    }
}
