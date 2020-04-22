package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;

public class EditProfilePhone extends AppCompatActivity {

    private TextView phone;
    private Button btn;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_phone);
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        phone=findViewById(R.id.phone_chnage);
        btn=findViewById(R.id.change_phone_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhone();
            }
        });
    }

    private void updatePhone() {

        final ProgressDialog progressDialog =new ProgressDialog(EditProfilePhone.this);
        progressDialog.setMessage("Signing In");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone.getText().toString());
        mDatabaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Phone Number Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),EditProfile.class);
                    progressDialog.dismiss();
                    startActivity(i);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
