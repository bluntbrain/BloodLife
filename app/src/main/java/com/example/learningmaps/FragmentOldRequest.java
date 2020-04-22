package com.example.learningmaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentOldRequest extends Fragment {

    private RecyclerView mRecyclerView;
    private OldRequestCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AddingItemsOldRequests> data;
    private DatabaseReference mReference;
    private FirebaseUser mUser;
    private ConstraintLayout getconnect;
    private TextView connect;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_requested,container,false);
        mUser=FirebaseAuth.getInstance().getCurrentUser();

        data=new ArrayList<>();
        getconnect=view.findViewById(R.id.getconnect);
        connect=view.findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(view.getContext(),SearchUsers.class);
                startActivity(intent);
            }
        });
        mRecyclerView=view.findViewById(R.id.oldrequestcontainer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        adddatatolist();



        return view;
    }

    public void adddatatolist(){

        mReference= FirebaseDatabase.getInstance().getReference("BloodRequests");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("id").getValue().toString().equals(mUser.getUid())){
                        String victim_type = snapshot.child("victim_bloodtype").getValue().toString();
                        String victim_name = snapshot.child("victim_name").getValue().toString();
                        String victim_place = snapshot.child("victim_hospital").getValue().toString();
                        String victim_phone =snapshot.child("phone").getValue().toString();
                        String victim_status =snapshot.child("victim_status").getValue().toString();
                        String victim_units = "20";
                        data.add(new AddingItemsOldRequests(victim_name,victim_type,victim_place,victim_phone,victim_status,victim_units));
                        getconnect.setVisibility(View.VISIBLE);
                    }
                }
                if(data.size()!=0){
                    view.findViewById(R.id.textofnorequest).setAlpha(0f);
                }
                mAdapter= new OldRequestCustomAdapter(data);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OldRequestCustomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                        TextView dataset = v.findViewById(R.id.dataset);
                        String sheetdata = dataset.getText().toString();
                        Intent i = new Intent(view.getContext(),DeleteRequest.class);
                        i.putExtra("dataset",sheetdata);
                        startActivity(i);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
