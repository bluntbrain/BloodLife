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
import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;

public class Request extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RequestsCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mReference;
    private ArrayList<AddingItemsRequests> data;
    private GifImageView loading;
    private HashMap<Integer,ModelBottomSheetRequest> dataofall;

    BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        loading=findViewById(R.id.request_loading);
        mBottomNavigationView=findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setSelectedItemId(R.id.request_blood_icon);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        dataofall=new HashMap<>();
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
                int count=0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                        String victim_type = snapshot.child("victim_bloodtype").getValue().toString();
                        String victim_name = snapshot.child("victim_name").getValue().toString();
                        String victim_place = snapshot.child("victim_hospital").getValue().toString();
                        String victim_phone = snapshot.child("phone").getValue().toString();
                        String victim_status = snapshot.child("victim_status").getValue().toString();
                        String victim_units = snapshot.child("units").getValue().toString();
                        data.add(new AddingItemsRequests(victim_name, victim_type, victim_place, victim_phone, victim_status, victim_units));
                        dataofall.put(count, new ModelBottomSheetRequest(victim_name, victim_units, victim_type, victim_status, snapshot.child("victim_gender").getValue().toString(), victim_place, victim_phone));
                        count++;

                }

                if(count==0){
                    findViewById(R.id.here).setVisibility(View.VISIBLE);
                }

                mAdapter= new RequestsCustomAdapter(data);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new RequestsCustomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {

                        View v= mRecyclerView.getLayoutManager().findViewByPosition(position);
                        ModelBottomSheetRequest ans=dataofall.get(position);
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Request.this, R.style.BottomSheetDialogTheme);
                        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_request_bottom_sheet, (RelativeLayout) findViewById(R.id.bottomsheetcontainer));

                        final TextView namesheet =bottomSheetView.findViewById(R.id.namesheet);//namesheet.setText(allsheetdata[0]);
                        final TextView statussheet =bottomSheetView.findViewById(R.id.statussheets);//statussheet.setText(allsheetdata[3]);
                        final TextView unitssheet =bottomSheetView.findViewById(R.id.unitssheet);//unitssheet.setText(allsheetdata[1]);
                        final TextView bloodtypesheet =bottomSheetView.findViewById(R.id.bloodtypesheet);//bloodtypesheet.setText(allsheetdata[2]);
                        final TextView gendersheet =bottomSheetView.findViewById(R.id.gendersheet);////gendersheet.setText(allsheetdata[4]);
                        final TextView hospitalsheet =bottomSheetView.findViewById(R.id.locationsheet);//hospitalsheet.setText(allsheetdata[5]);
                        final TextView sharebtn = bottomSheetView.findViewById(R.id.share_request_btn);//phonesheet.setText(allsheetdata[6]);
                        final TextView phonesheet =bottomSheetView.findViewById(R.id.phonesheet);//phonesheet.setText(allsheetdata[6]);

                        namesheet.setText(ans.getName());
                        statussheet.setText(ans.getStatus());
                        unitssheet.setText(ans.getUnits());
                        bloodtypesheet.setText(ans.getBloodtype());
                        gendersheet.setText(ans.getGender());
                        hospitalsheet.setText(ans.getPlace());
                        phonesheet.setText(ans.getPhone());

                        sharebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "*Bloodtype "+ bloodtypesheet.getText().toString() +" is urgently required at "
                                        + hospitalsheet.getText().toString()+"*" +"\nDetails -\n"+"Name : " +namesheet.getText().toString()+ "\nUnits required : "+unitssheet.getText().toString()+
                                        "\nEmergency Status : "+statussheet.getText().toString()+"\nContact : "+phonesheet.getText().toString()+
                                        "\nHelp to Donate and bring life back to power "+getEmojiByUnicode(0x270B)+
                                        "\n\nHelp to save more lives by becoming donors at our platform BloodLife, Manipal's first blood donation platform"+
                                        "\napp link...");
                                shareIntent.setType("text/plain");
                                startActivity(Intent.createChooser(shareIntent, "Share with"));
                            }
                        });

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

                    Intent a=new Intent(Request.this,LeaderBoard.class);
                    startActivity(a);
                    break;

                case R.id.profile_icon:

                    Intent b=new Intent(Request.this,ProfileFinal.class);
                    startActivity(b);
                    break;

                case R.id.request_blood_icon:

                    break;

            }


            return false;
        }
    };

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
