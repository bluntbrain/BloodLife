package com.example.learningmaps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class RequestBlood extends AppCompatActivity {
    private LatLng mycoordinates;
    private ImageView i1,i2;
    private Button done;
    private String Status,BloodGroup,Gender;
    private EditText name, mobile, units, place;
    private String CurrentUserName;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private String tokenId;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private DatabaseReference mReference;
    private FirebaseUser mUser;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dob;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        mUser= FirebaseAuth.getInstance().getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();gettingCurrentUserName();

        gettingCurrentTokenID();
        tryFCM();
        final Bundle bundle = getIntent().getParcelableExtra("bundle");
        mycoordinates = bundle.getParcelable("userlocation");
        //SPINNER CODE

        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        place=findViewById(R.id.place);
        units=findViewById(R.id.units);

        dob=findViewById(R.id.dateofbirth);
        calendar = Calendar.getInstance();
        //SimpleDateFormat myDateFormat = new SimpleDateFormat("MM.dd.yyyy.");
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

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

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long nextdate=1000*60*60*24;
                long maxdate=1000*60*60*24*12;
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestBlood.this,myDateListener, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+nextdate);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()+maxdate);
                datePickerDialog.show();
            }


        });


//IMAGE CODE
        i1=findViewById(R.id.maleimage);
        i2=findViewById(R.id.femaleimage);
        done=findViewById(R.id.reques_btn);
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

                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(place.getText()) || TextUtils.isEmpty(units.getText()) || TextUtils.isEmpty(mobile.getText())
                        || TextUtils.isEmpty(Gender) || TextUtils.isEmpty(BloodGroup) || TextUtils.isEmpty(Status)) {
                    Toast.makeText(RequestBlood.this, "Fields Empty", Toast.LENGTH_LONG).show();
                }else{

                    builder = new AlertDialog.Builder(RequestBlood.this);
                    builder.setTitle("Request Blood");
                    builder.setMessage("No changes are allowed once request is made. Nearby donors will receive notification regarding your blood emergency");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uploadRequest();
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
            }
        });




    }

    private void gettingCurrentUserName(){

        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                CurrentUserName=dataSnapshot.child("name").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                CurrentUserName="Anomolous";


            }
        });

    }

    public void tryFCM(){

        FirebaseMessaging.getInstance().subscribeToTopic("donors").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestBlood.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadRequest(){
        final ProgressDialog progressDialog =new ProgressDialog(RequestBlood.this);
        progressDialog.setMessage("Request Uploading");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("id",mUser.getUid());
        hashMap.put("request_id",mUser.getUid()+name.getText().toString()+BloodGroup+units.getText().toString());
        hashMap.put("name",CurrentUserName);
        hashMap.put("victim_name",name.getText()+"");
        hashMap.put("victim_hospital",place.getText()+"");
        hashMap.put("victim_bloodtype",BloodGroup);
        hashMap.put("victim_status",Status);
        hashMap.put("victim_gender",Gender);
        hashMap.put("phone",mobile.getText().toString());
        hashMap.put("victim_lat",mycoordinates.latitude+"");
        hashMap.put("victim_long",mycoordinates.longitude+"");
        hashMap.put("units",units.getText().toString());
        hashMap.put("expiry_date",dob.getText().toString());


        mReference.child("BloodRequests").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    postNotification();
                    Toasty.success(getApplicationContext(), "Request Successful", Toast.LENGTH_LONG, true).show();
                    Intent i = new Intent(RequestBlood.this, MainActivity.class);
                    i.putExtra("ripple","yes");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    progressDialog.dismiss();
                    startActivity(i);
                    finish();


                }else{
                    Toast.makeText(RequestBlood.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RequestBlood.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


     DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            dob.setText(new StringBuilder().append(arg3).append("/")
                    .append(arg2).append("/").append(arg1));
        }
    };

    public void postNotification(){

        String[] quotes={"Take a step and do the nobel donation","You don't have to be a doctor to save lives",
        "Blood is meant to circulate, pass it around","Donate and make your blood divine"};

        Random rand = new Random();
        Integer int1= rand.nextInt(4);

        Map<String, Object> notificationmanager = new HashMap<>();
        notificationmanager.put("message",  quotes[int1]);
        notificationmanager.put("title", "Blood Type "+BloodGroup+" is required in neighborhood");

        mFirestore.collection("Users").document(mUser.getUid())
                .collection("Notifications")
                .add(notificationmanager)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestBlood.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void gettingCurrentTokenID(){
        mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {

                tokenId= FirebaseInstanceId.getInstance().getToken();
                Map<String,Object> tokenMap=new HashMap<>();
                tokenMap.put("token_id",tokenId);

                mFirestore.collection("Users").document(mUser.getUid()).set(tokenMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RequestBlood.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RequestBlood.this,e.getMessage(),Toast.LENGTH_SHORT).show();

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
}
