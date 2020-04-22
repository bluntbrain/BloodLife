package com.example.learningmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchUsers extends AppCompatActivity {

    private Spinner mSpinner;
    private String Bloodtype;
    private EditText searchUser;

    private RecyclerView mRecyclerView;
    private SearchUsersCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AddingItemsSearchUsers> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        data=new ArrayList<>();
        mRecyclerView=findViewById(R.id.search_user_container);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSpinner=findViewById(R.id.search_user_spinner);
        setSpinner();
        searchUser=findViewById(R.id.search_users_here);
        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                additemsname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setSpinner(){

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
        mSpinner.setAdapter(arrayAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bloodtype=parent.getItemAtPosition(position).toString();

                additemsbloodtype(Bloodtype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void additemsname(String s){
        Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                if (!searchUser.getText().toString().equals("")) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String innername = snapshot.child("name").getValue().toString();
                        String innerdp = snapshot.child("imageURL").getValue().toString();
                        String innertypr = snapshot.child("bloodtype").getValue().toString();
                        String inneremail = snapshot.child("email").getValue().toString();
                        data.add(new AddingItemsSearchUsers(innername, innerdp, innertypr, inneremail));
                    }
                    mAdapter = new SearchUsersCustomAdapter(data);
                    mRecyclerView.setAdapter(mAdapter);
                }

                mAdapter.setOnItemClickListener(new SearchUsersCustomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {


                        View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                        TextView n=v.findViewById(R.id.user_names);
                        TextView p=v.findViewById(R.id.search_users_email);

                        String message ="Hey "+n.getText().toString()+"....";
                        String subject="Blood Requirement";
                        String[] to = {p.getText().toString()};
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.setData(Uri.parse("mailto:"));
                        email.setType("text/plain");

                        email.putExtra(Intent.EXTRA_EMAIL, to);
                        email.putExtra(Intent.EXTRA_SUBJECT, subject);
                        email.putExtra(Intent.EXTRA_TEXT, message);

                        email.setType("message/rfc822");
                        try {
                            startActivity(Intent.createChooser(email, "Send mail"));
                            // finish();
                            //Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void additemsbloodtype(final String bloodtype){
        data.clear();

        Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("bloodtype");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("bloodtype").getValue().toString().equals(bloodtype)) {
                        String innername = snapshot.child("name").getValue().toString();
                        String innerdp = snapshot.child("imageURL").getValue().toString();
                        String innertypr = snapshot.child("bloodtype").getValue().toString();
                        String inneremail = snapshot.child("email").getValue().toString();
                        data.add(new AddingItemsSearchUsers(innername, innerdp, innertypr, inneremail));
                    }
                    mAdapter = new SearchUsersCustomAdapter(data);
                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.setOnItemClickListener(new SearchUsersCustomAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                            TextView n=v.findViewById(R.id.user_names);
                            TextView p=v.findViewById(R.id.search_users_email);

                            String message ="Hey "+n.getText().toString()+"....";
                            String subject="Blood Requirement";
                            String[] to = {p.getText().toString()};
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.setData(Uri.parse("mailto:"));
                            email.setType("text/plain");

                            email.putExtra(Intent.EXTRA_EMAIL, to);
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);

                            email.setType("message/rfc822");
                            try {
                                startActivity(Intent.createChooser(email, "Send mail"));
                                // finish();
                                //Log.i("Finished sending email...", "");
                            } catch (android.content.ActivityNotFoundException ex) {
                            }
                        }
                        }
                    );
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
