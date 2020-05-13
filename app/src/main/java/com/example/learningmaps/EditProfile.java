package com.example.learningmaps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class EditProfile extends AppCompatActivity {

    private TextView edit_profile_email;
    private TextView edit_profile_password;
    private TextView edit_profile_phone;
    private ImageView delete_profile_btn;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private DatabaseReference mReference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_profile_phone = findViewById(R.id.edit_profile_phone);
        edit_profile_email = findViewById(R.id.edit_profile_email);
        edit_profile_password = findViewById(R.id.edit_profile_password);
        delete_profile_btn = findViewById(R.id.delete_profile_btn);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        edit_profile_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfilePassword.class);
                startActivity(intent);
            }
        });

        edit_profile_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileEmail.class);
                startActivity(intent);
            }
        });

        edit_profile_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfilePhone.class);
                startActivity(intent);
            }
        });


        delete_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle("Delete Account ?");
                builder.setMessage("You won't be able to access Blood Request from this profile");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void deleteUser() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        tryFCM();
        final String id = mUser.getUid();

        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", "--");
                    hashMap.put("name", "--");
                    hashMap.put("bloodtype", "--");
                    mReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(EditProfile.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            progressDialog.dismiss();
                            startActivity(i);
                        }
                    });
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "error in deleting", Toast.LENGTH_LONG, true).show();

            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(EditProfile.this,ProfileFinal.class));

    }

    public void tryFCM(){

        FirebaseMessaging.getInstance().unsubscribeFromTopic("donors").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
