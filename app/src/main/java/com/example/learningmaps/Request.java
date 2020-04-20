package com.example.learningmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class Request extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RequestsCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
   // private RippleBackground mRippleBackground;
   // private ImageView mImageView;
    private DatabaseReference mReference;
    private ArrayList<AddingItemsRequests> data;
    private GifImageView loading;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
       // mImageView=findViewById(R.id.requests_backbutton);
       // mRippleBackground=findViewById(R.id.ripple);
       // mRippleBackground.startRippleAnimation();
        loading=findViewById(R.id.request_loading);
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        data=new ArrayList<>();

        addlistdata();

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);






    }

    public  void addlistdata( )
    {



        mReference= FirebaseDatabase.getInstance().getReference("BloodRequests");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String victim_type = snapshot.child("victim_bloodtype").getValue().toString();
                    String victim_name = snapshot.child("victim_name").getValue().toString();
                    String victim_place = snapshot.child("victim_hospital").getValue().toString();
                    String victim_phone =snapshot.child("phone").getValue().toString();
                    String victim_status =snapshot.child("victim_status").getValue().toString();
                    String victim_units = snapshot.child("units").getValue().toString();
                    data.add(new AddingItemsRequests(victim_name,victim_type,victim_place,victim_phone,victim_status,victim_units));
                }

                mAdapter= new RequestsCustomAdapter(data);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new RequestsCustomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                        TextView alldata= v.findViewById(R.id.dataofitem);
                        String sheetdata = alldata.getText().toString();
                        String[] allsheetdata = sheetdata.split("-");
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Request.this, R.style.BottomSheetDialogTheme);
                        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_request_bottom_sheet, (RelativeLayout) findViewById(R.id.bottomsheetcontainer));

                        TextView namesheet =bottomSheetView.findViewById(R.id.namesheet);namesheet.setText(allsheetdata[0]);
                        TextView statussheet =bottomSheetView.findViewById(R.id.statussheets);statussheet.setText(allsheetdata[3]);
                        TextView unitssheet =bottomSheetView.findViewById(R.id.unitssheet);unitssheet.setText(allsheetdata[1]);
                        TextView bloodtypesheet =bottomSheetView.findViewById(R.id.bloodtypesheet);bloodtypesheet.setText(allsheetdata[2]);
                        TextView gendersheet =bottomSheetView.findViewById(R.id.gendersheet);gendersheet.setText(allsheetdata[4]);
                        TextView hospitalsheet =bottomSheetView.findViewById(R.id.locationsheet);hospitalsheet.setText(allsheetdata[5]);
                        final TextView phonesheet =bottomSheetView.findViewById(R.id.phonesheet);phonesheet.setText(allsheetdata[6]);

                        Button btnsheet = bottomSheetView.findViewById(R.id.buttonsheet);
                        btnsheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+phonesheet.getText().toString()));
                                startActivity(intent);
                            }
                        });



                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                    }
                });

                loading.setVisibility(View.GONE);
                mRecyclerView.setAlpha(1f);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Request.this,"Server Respond :"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }













    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.map_icon:
                    Intent c=new Intent(Request.this,MainActivity.class);
                    startActivity(c);
                    break;

                case R.id.leaderboard_icon:
                    Intent a=new Intent(Request.this,BheroLoading.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:
                    Intent b=new Intent(Request.this,Profile.class);
                    startActivity(b);
                    break;

                case R.id.request_blood:

                    break;

            }


            return false;
        }
    };
}
