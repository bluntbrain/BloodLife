package com.example.learningmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteRequest extends AppCompatActivity {

    private TextView deletename,deletetype,deletestatus,deleteunits,deletegender,deletelocation,deletemobile;
    private EditText searchuser;
    private Button adddonorsbtn, buttonfinaldelete;
    private Integer flag;

    private RecyclerView mRecyclerView;
    private SearchDonorsCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AddingItemsSearchDonors> data;

    private RecyclerView addedRecyclerView;
    private AddedDonorCustomAdapter addedAdapter;
    private RecyclerView.LayoutManager addedLayoutManager;
    private ArrayList<AddingItemsAddedDonors> addeddata;

    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_request);
        data=new ArrayList<>();
        addeddata=new ArrayList<>();
        flag=0;
        deletename=findViewById(R.id.deletename);
        buttonfinaldelete=findViewById(R.id.buttonfinaldelete);
        deletegender=findViewById(R.id.deletegender);
        deletelocation=findViewById(R.id.deletelocation);
        deletemobile=findViewById(R.id.deletemobile);
        deletestatus=findViewById(R.id.deletestatus);
        deleteunits=findViewById(R.id.deleteunits);
        deletetype=findViewById(R.id.deletetype);
        adddonorsbtn=findViewById(R.id.adddonorsbtn);
        searchuser=findViewById(R.id.searchdonors);

        Bundle b = getIntent().getExtras();
        String j =(String) b.get("dataset");

        String[] actualdata= j.split("=");

        deletename.setText(actualdata[0]);
        deleteunits.setText("units required: "+actualdata[1]);
        deletetype.setText(actualdata[2]);
        deletestatus.setText(actualdata[3]);
        deletegender.setText("/ "+actualdata[4]);
        deletelocation.setText(actualdata[5]);
        deletemobile.setText(actualdata[6]);

        mRecyclerView=findViewById(R.id.searchdonorcontainer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        addedRecyclerView=findViewById(R.id.addeddonorcontainer);
        //addedRecyclerView.setHasFixedSize(true);
        addedLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        addedRecyclerView.setLayoutManager(addedLayoutManager);

        adddonorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchuser.setAlpha(1f);
            }
        });

        searchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchUsers(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        buttonfinaldelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mReference= FirebaseDatabase.getInstance().getReference("BloodRequests");
                final ProgressDialog progressDialog =new ProgressDialog(DeleteRequest.this);
                progressDialog.setMessage("Deleting Request");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Query query =FirebaseDatabase.getInstance().getReference("BloodRequests").orderByChild("victim_name").equalTo(deletename.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){


                                        if(flag!=0) {
                                            mReference = FirebaseDatabase.getInstance().getReference();

                                            List<HashMap<String, String>> hashMapList = new ArrayList<>();

                                            for (int i = 0; i < addedAdapter.getItemCount(); i++) {

                                                View v = addedRecyclerView.getLayoutManager().findViewByPosition(i);
                                                TextView name = v.findViewById(R.id.addeddonorname);
                                                TextView idplusurl = v.findViewById(R.id.addeddonorid);
                                                String[] yoolo = idplusurl.getText().toString().split("=,=,=");

                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("name", name.getText().toString());
                                                hashMap.put("imageURl", yoolo[1]);
                                                hashMap.put("id", yoolo[0]);

                                                hashMapList.add(hashMap);
                                            }

                                            for (int j = 0; j < hashMapList.size() - 1; j++) {
                                                mReference.child("Donoted").push().setValue(hashMapList.get(j));
                                            }
                                            mReference.child("Donoted").push().setValue(hashMapList.get(hashMapList.size() - 1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Request Deleted", Toast.LENGTH_LONG).show();

                                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    progressDialog.dismiss();
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Request Deleted", Toast.LENGTH_LONG).show();

                                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    progressDialog.dismiss();
                                                    startActivity(i);
                                                    finish();
                                                }
                                            });

                                        }else{
                                            Toast.makeText(getApplicationContext(), "Request Deleted", Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            progressDialog.dismiss();
                                            startActivity(i);
                                            finish();
                                        }

                                    }else{
                                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }


                });
            }
        });
    }

    public void searchUsers(String s){
        final FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();
        Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                if(!searchuser.getText().toString().equals("")){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String searchid =snapshot.child("id").getValue().toString()+"=,=,="+snapshot.child("imageURL").getValue().toString();
                        String innername= snapshot.child("name").getValue().toString();
                        String innerdp =snapshot.child("imageURL").getValue().toString();
                        String[] fooor=searchid.split("=,=,=");
                        if (!fooor[0].equals(fUser.getUid())) {

                            data.add(new AddingItemsSearchDonors(innername,innerdp,searchid));
                        }

                    }
                    mAdapter= new SearchDonorsCustomAdapter(data);
                    mRecyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(new SearchDonorsCustomAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {

                            flag=1;

                            View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                            TextView nameclick =v.findViewById(R.id.searchdonorsname);
                            TextView idclick =v.findViewById(R.id.searchdonor_id);
                            //CircleImageView urlclick =v.findViewById(R.id.addeddonordp);

                            String foolo=idclick.getText().toString();
                            String[] yoolo =foolo.split("=,=,=");
                           // Toast.makeText(getApplicationContext(),yoolo[0],Toast.LENGTH_SHORT).show();

                            addeddata.add(new AddingItemsAddedDonors(nameclick.getText().toString(),yoolo[1],idclick.getText().toString()));
                            addedAdapter=new AddedDonorCustomAdapter(addeddata);
                            addedRecyclerView.setAdapter(addedAdapter);

                            TextView textaddeddonor =findViewById(R.id.textdonoradded);
                            textaddeddonor.setAlpha(1f);
                            searchuser.setText("");


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
