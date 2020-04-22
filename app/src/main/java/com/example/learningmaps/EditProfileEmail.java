package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileEmail extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText new_email;
    private FirebaseUser mUser;
    private Button btn;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_email);

        email=findViewById(R.id.username_change_email);
        password=findViewById(R.id.password_change_email);
        new_email=findViewById(R.id.password_change_email2);
        btn=findViewById(R.id.change_email_btn);

        mUser= FirebaseAuth.getInstance().getCurrentUser();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(new_email.getText()))
                {
                    Toast.makeText(getApplicationContext(),"Fields Empty",Toast.LENGTH_LONG).show();

                }else {
                    changeEmail();
                }
            }
        });

    }
    private void changeEmail(){

        final ProgressDialog progressDialog =new ProgressDialog(EditProfileEmail.this);
        progressDialog.setMessage("Making Changes");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), password.getText().toString());
        mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateEmail(new_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                mDatabaseReference= FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
                                HashMap<String,Object> hashMap= new HashMap<>();
                                hashMap.put("email",new_email.getText().toString());
                                mDatabaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(),"Email Updated",Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(getApplicationContext(),EditProfile.class);
                                        progressDialog.dismiss();
                                        startActivity(i);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                        user.updateEmail(email.getText().toString());
                                    }
                                });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}
