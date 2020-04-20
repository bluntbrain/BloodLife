package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private ImageView male,female;
    private Button done;
    private EditText name,email,phoneno,password;
    private String BloodGroup,gender;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("EpQmd1UQ4LCPsqG65sqHKSJ04Yk5V1e4VK631IKc")
                .clientKey("Vm0egZoggfRMZFVfXCaOkm3XfErzApy6BXqZjHUk")
                .server("https://parseapi.back4app.com")
                .build()
        );
//SPINNER CODE
        mAuth=FirebaseAuth.getInstance();

        final Spinner spinner = findViewById(R.id.spinner);
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

        name=findViewById(R.id.signup_name);
       // userid=findViewById(R.id.signup_userid);
        email=findViewById(R.id.signup_email);
        phoneno=findViewById(R.id.signup_phoneno);
        password=findViewById(R.id.signup_password);
        male=findViewById(R.id.maleimage);
        female=findViewById(R.id.femaleimage);
        done=findViewById(R.id.signup_done);


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setImageResource(R.drawable.malepink);
                gender="male";
                female.setImageResource(R.drawable.femalegrey);
                //Toast.makeText(getApplicationContext(),"MALE",Toast.LENGTH_LONG).show();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setImageResource(R.drawable.femalepink);
                gender="female";
                male.setImageResource(R.drawable.malegrey);
                //Toast.makeText(getApplicationContext(),"FEMALE",Toast.LENGTH_LONG).show();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

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

    private void registerUser()
    {
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(phoneno.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(BloodGroup))
        {
            Toast.makeText(SignupActivity.this,"Fields Empty",Toast.LENGTH_LONG).show();
        }else if(password.length() < 4 || password.length() > 10)
        {
            Toast.makeText(SignupActivity.this,"A min 4 length password must be set",Toast.LENGTH_LONG).show();
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            Toast.makeText(SignupActivity.this,"Please enter email in correct format",Toast.LENGTH_LONG).show();

        }else{
            final ProgressDialog progressDialog=new ProgressDialog(SignupActivity.this);
            progressDialog.setMessage("Signing Up");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email.getText()+"",password.getText()+"").addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        String userid =mAuth.getCurrentUser().getUid();
                        mDatabaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("id",userid);
                        hashMap.put("name",name.getText()+"");
                        hashMap.put("gender",gender);
                        hashMap.put("email",email.getText()+"");
                        hashMap.put("units_donated","0");
                        hashMap.put("bloodtype",BloodGroup);
                        hashMap.put("phone",phoneno.getText()+"");

                        mDatabaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Intent intent=new Intent(SignupActivity.this,UploadPicture.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    progressDialog.dismiss();
                                    startActivity(intent);
                                    finish();

                                }else{
                                    Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });



                    }else
                    {
                        Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }

                }
            });


        }
    }
}
