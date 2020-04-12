package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private ImageView backfromforgot;
    private EditText emailforgot;
    private Button btnforgot;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backfromforgot=findViewById(R.id.backfromforgot);
        emailforgot=findViewById(R.id.emailforgot);
        btnforgot=findViewById(R.id.btnforgot);
        firebaseAuth=FirebaseAuth.getInstance();

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=  emailforgot.getText().toString();
                if(email.equals(""))
                {
                    Toast.makeText(ForgotPassword.this,"Email Required",Toast.LENGTH_SHORT).show();
                }else{
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ForgotPassword.this,"Please Check Your email to Reset Password",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent( ForgotPassword.this,LoginActivity.class));
                                }else {
                                    Toast.makeText(ForgotPassword.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ForgotPassword.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                }
            }
        });


    }
}
